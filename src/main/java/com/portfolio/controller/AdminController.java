package com.portfolio.controller;

import com.portfolio.dto.CertificateDto;
import com.portfolio.dto.ProjectDto;
import com.portfolio.entity.Certificate;
import com.portfolio.entity.Project;
import com.portfolio.service.CertificateService;
import com.portfolio.service.ContactService;
import com.portfolio.service.FileStorageService;
import com.portfolio.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProjectService projectService;
    private final CertificateService certificateService;
    private final ContactService contactService;
    private final FileStorageService fileStorageService;

    public AdminController(ProjectService projectService,
                           CertificateService certificateService,
                           ContactService contactService,
                           FileStorageService fileStorageService) {
        this.projectService = projectService;
        this.certificateService = certificateService;
        this.contactService = contactService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "admin/login";
    }

    @GetMapping
    public String dashboard(Model model) {
        var projects = projectService.findAllProjects();
        var certificates = certificateService.findAllCertificates();
        var messages = contactService.findAllMessages();

        model.addAttribute("projects", projects);
        model.addAttribute("certificates", certificates);
        model.addAttribute("messages", messages);
        model.addAttribute("projectCount", projects.size());
        model.addAttribute("certificateCount", certificates.size());
        model.addAttribute("messageCount", messages.size());
        model.addAttribute("unreadCount", contactService.countUnreadMessages());
        return "admin/dashboard";
    }

    // --- Project CRUD ---

    @GetMapping("/projects/new")
    public String newProjectForm(Model model) {
        model.addAttribute("projectForm", new ProjectDto());
        model.addAttribute("isEdit", false);
        return "admin/project-form";
    }

    @PostMapping("/projects")
    public String createProject(@Valid @ModelAttribute("projectForm") ProjectDto projectForm,
                                BindingResult bindingResult,
                                @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "admin/project-form";
        }
        if (imageFile != null && !imageFile.isEmpty()) {
            String storedPath = fileStorageService.storeImage(imageFile, "projects");
            projectForm.setImageUrl(storedPath);
        } else if (projectForm.getImageUrl() == null || projectForm.getImageUrl().isBlank()) {
            projectForm.setImageUrl("/images/projects/default-project.svg");
        }
        projectService.createProject(projectForm);
        redirectAttributes.addFlashAttribute("adminSuccess", "Project created successfully.");
        return "redirect:/admin#projects-panel";
    }

    @GetMapping("/projects/{id}/edit")
    public String editProjectForm(@PathVariable Long id, Model model) {
        Project project = projectService.findById(id);
        model.addAttribute("projectForm", projectService.toDto(project));
        model.addAttribute("isEdit", true);
        return "admin/project-form";
    }

    @PostMapping("/projects/{id}")
    public String updateProject(@PathVariable Long id,
                                @Valid @ModelAttribute("projectForm") ProjectDto projectForm,
                                BindingResult bindingResult,
                                @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            projectForm.setId(id);
            model.addAttribute("isEdit", true);
            return "admin/project-form";
        }
        Project existing = projectService.findById(id);
        if (imageFile != null && !imageFile.isEmpty()) {
            String storedPath = fileStorageService.storeImage(imageFile, "projects");
            fileStorageService.deleteStoredFileIfLocalUpload(existing.getImageUrl());
            projectForm.setImageUrl(storedPath);
        } else if (projectForm.getImageUrl() == null || projectForm.getImageUrl().isBlank()) {
            projectForm.setImageUrl(existing.getImageUrl());
        }
        projectService.updateProject(id, projectForm);
        redirectAttributes.addFlashAttribute("adminSuccess", "Project updated successfully.");
        return "redirect:/admin#projects-panel";
    }

    @PostMapping("/projects/{id}/delete")
    public String deleteProject(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Project existing = projectService.findById(id);
        fileStorageService.deleteStoredFileIfLocalUpload(existing.getImageUrl());
        projectService.deleteProject(id);
        redirectAttributes.addFlashAttribute("adminSuccess", "Project deleted successfully.");
        return "redirect:/admin#projects-panel";
    }

    // --- Certificate CRUD ---

    @GetMapping("/certificates/new")
    public String newCertificateForm(Model model) {
        model.addAttribute("certificateForm", new CertificateDto());
        model.addAttribute("isEdit", false);
        return "admin/certificate-form";
    }

    @PostMapping("/certificates")
    public String createCertificate(@Valid @ModelAttribute("certificateForm") CertificateDto certificateForm,
                                    BindingResult bindingResult,
                                    @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                                    Model model,
                                    RedirectAttributes redirectAttributes) {
        boolean hasUpload = imageFile != null && !imageFile.isEmpty();
        boolean hasUrl = certificateForm.getImageUrl() != null && !certificateForm.getImageUrl().isBlank();
        if (!hasUpload && !hasUrl) {
            bindingResult.rejectValue("imageUrl", "image.required", "Please upload a certificate image or provide an image URL.");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "admin/certificate-form";
        }
        if (hasUpload) {
            certificateForm.setImageUrl(fileStorageService.storeImage(imageFile, "certificates"));
        }
        certificateService.createCertificate(certificateForm);
        redirectAttributes.addFlashAttribute("adminSuccess", "Certificate added successfully.");
        return "redirect:/admin#certificates-panel";
    }

    @GetMapping("/certificates/{id}/edit")
    public String editCertificateForm(@PathVariable Long id, Model model) {
        Certificate certificate = certificateService.findById(id);
        model.addAttribute("certificateForm", certificateService.toDto(certificate));
        model.addAttribute("isEdit", true);
        return "admin/certificate-form";
    }

    @PostMapping("/certificates/{id}")
    public String updateCertificate(@PathVariable Long id,
                                    @Valid @ModelAttribute("certificateForm") CertificateDto certificateForm,
                                    BindingResult bindingResult,
                                    @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                                    Model model,
                                    RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            certificateForm.setId(id);
            model.addAttribute("isEdit", true);
            return "admin/certificate-form";
        }
        Certificate existing = certificateService.findById(id);
        if (imageFile != null && !imageFile.isEmpty()) {
            String storedPath = fileStorageService.storeImage(imageFile, "certificates");
            fileStorageService.deleteStoredFileIfLocalUpload(existing.getImageUrl());
            certificateForm.setImageUrl(storedPath);
        } else if (certificateForm.getImageUrl() == null || certificateForm.getImageUrl().isBlank()) {
            certificateForm.setImageUrl(existing.getImageUrl());
        }
        certificateService.updateCertificate(id, certificateForm);
        redirectAttributes.addFlashAttribute("adminSuccess", "Certificate updated successfully.");
        return "redirect:/admin#certificates-panel";
    }

    @PostMapping("/certificates/{id}/delete")
    public String deleteCertificate(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Certificate existing = certificateService.findById(id);
        fileStorageService.deleteStoredFileIfLocalUpload(existing.getImageUrl());
        certificateService.deleteCertificate(id);
        redirectAttributes.addFlashAttribute("adminSuccess", "Certificate deleted successfully.");
        return "redirect:/admin#certificates-panel";
    }

    // --- Contact Messages ---

    @PostMapping("/messages/{id}/read")
    public String markMessageRead(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        contactService.markAsRead(id);
        redirectAttributes.addFlashAttribute("adminSuccess", "Message marked as read.");
        return "redirect:/admin#messages-panel";
    }

    @PostMapping("/messages/{id}/delete")
    public String deleteMessage(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        contactService.deleteMessage(id);
        redirectAttributes.addFlashAttribute("adminSuccess", "Message deleted.");
        return "redirect:/admin#messages-panel";
    }
}
