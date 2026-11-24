package com.portfolio.controller;

import com.portfolio.entity.Certificate;
import com.portfolio.service.CertificateService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CertificateController {

    private final CertificateService certificateService;

    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping("/certificates")
    public String listCertificates(Model model) {
        model.addAttribute("certificates", certificateService.findAllCertificates());
        return "pages/certificates";
    }

    @GetMapping("/certificates/{id}")
    public String viewCertificate(@PathVariable Long id, Model model) {
        Certificate certificate = certificateService.findById(id);
        model.addAttribute("certificate", certificate);
        return "pages/certificate-detail";
    }

    @GetMapping("/api/certificates")
    @ResponseBody
    public ResponseEntity<List<Certificate>> getCertificatesJson() {
        return ResponseEntity.ok(certificateService.findAllCertificates());
    }
}
