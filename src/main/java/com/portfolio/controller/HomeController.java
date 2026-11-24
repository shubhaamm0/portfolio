package com.portfolio.controller;

import com.portfolio.config.PortfolioProfile;
import com.portfolio.dto.ContactMessageDto;
import com.portfolio.service.CertificateService;
import com.portfolio.service.ProjectService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ProjectService projectService;
    private final CertificateService certificateService;
    private final PortfolioProfile portfolioProfile;

    public HomeController(ProjectService projectService,
                          CertificateService certificateService,
                          PortfolioProfile portfolioProfile) {
        this.projectService = projectService;
        this.certificateService = certificateService;
        this.portfolioProfile = portfolioProfile;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("projects", projectService.findFeaturedProjects());
        model.addAttribute("projectService", projectService);
        model.addAttribute("certificates", certificateService.findAllCertificates());
        model.addAttribute("profile", portfolioProfile);
        if (!model.containsAttribute("contactForm")) {
            model.addAttribute("contactForm", new ContactMessageDto());
        }
        return "pages/index";
    }

    @GetMapping("/resume/download")
    public ResponseEntity<Resource> downloadResume() {
        Resource resource = new ClassPathResource("static/files/Shubham_Kumar_Rai_Resume.pdf");
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Shubham_Kumar_Rai_Resume.pdf\"")
                .body(resource);
    }

    @GetMapping("/resume/view")
    public ResponseEntity<Resource> viewResume() {
        Resource resource = new ClassPathResource("static/files/Shubham_Kumar_Rai_Resume.pdf");
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"Shubham_Kumar_Rai_Resume.pdf\"")
                .body(resource);
    }
}
