package com.portfolio.controller;

import com.portfolio.config.PortfolioProfile;
import com.portfolio.dto.ApiResponse;
import com.portfolio.dto.ContactMessageDto;
import com.portfolio.service.CertificateService;
import com.portfolio.service.ContactService;
import com.portfolio.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ContactController {

    private final ContactService contactService;
    private final ProjectService projectService;
    private final CertificateService certificateService;
    private final PortfolioProfile portfolioProfile;

    public ContactController(ContactService contactService,
                             ProjectService projectService,
                             CertificateService certificateService,
                             PortfolioProfile portfolioProfile) {
        this.contactService = contactService;
        this.projectService = projectService;
        this.certificateService = certificateService;
        this.portfolioProfile = portfolioProfile;
    }

    @PostMapping("/contact")
    public String submitContactForm(@Valid @ModelAttribute("contactForm") ContactMessageDto contactForm,
                                    BindingResult bindingResult,
                                    Model model,
                                    RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("projects", projectService.findFeaturedProjects());
            model.addAttribute("projectService", projectService);
            model.addAttribute("certificates", certificateService.findAllCertificates());
            model.addAttribute("profile", portfolioProfile);
            model.addAttribute("contactError", "Please correct the highlighted fields and try again.");
            return "pages/index";
        }

        contactService.saveMessage(contactForm);
        redirectAttributes.addFlashAttribute("contactSuccess",
                "Thank you for reaching out! Your message has been received and saved.");
        return "redirect:/#contact";
    }

    @PostMapping("/api/contact")
    @ResponseBody
    public ResponseEntity<ApiResponse> submitContactJson(@Valid @RequestBody ContactMessageDto dto) {
        contactService.saveMessage(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "Thank you! Your message has been sent successfully."));
    }
}
