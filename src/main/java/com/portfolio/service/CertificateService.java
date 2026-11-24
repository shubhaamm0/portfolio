package com.portfolio.service;

import com.portfolio.dto.CertificateDto;
import com.portfolio.entity.Certificate;
import com.portfolio.exception.ResourceNotFoundException;
import com.portfolio.repository.CertificateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CertificateService {

    private final CertificateRepository certificateRepository;

    public CertificateService(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    @Transactional(readOnly = true)
    public List<Certificate> findAllCertificates() {
        return certificateRepository.findAllByOrderByDisplayOrderAscCreatedAtDesc();
    }

    @Transactional(readOnly = true)
    public Certificate findById(Long id) {
        return certificateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Certificate not found with id: " + id));
    }

    @Transactional
    public Certificate createCertificate(CertificateDto dto) {
        Certificate certificate = new Certificate();
        mapDtoToEntity(dto, certificate);
        return certificateRepository.save(certificate);
    }

    @Transactional
    public Certificate updateCertificate(Long id, CertificateDto dto) {
        Certificate existing = findById(id);
        mapDtoToEntity(dto, existing);
        return certificateRepository.save(existing);
    }

    @Transactional
    public void deleteCertificate(Long id) {
        Certificate existing = findById(id);
        certificateRepository.delete(existing);
    }

    public CertificateDto toDto(Certificate cert) {
        CertificateDto dto = new CertificateDto();
        dto.setId(cert.getId());
        dto.setTitle(cert.getTitle());
        dto.setIssuer(cert.getIssuer());
        dto.setIssueDate(cert.getIssueDate());
        dto.setCredentialId(cert.getCredentialId());
        dto.setCredentialUrl(cert.getCredentialUrl());
        dto.setImageUrl(cert.getImageUrl());
        dto.setPdfUrl(cert.getPdfUrl());
        dto.setDescription(cert.getDescription());
        dto.setDisplayOrder(cert.getDisplayOrder());
        return dto;
    }

    private void mapDtoToEntity(CertificateDto dto, Certificate cert) {
        cert.setTitle(dto.getTitle().trim());
        cert.setIssuer(dto.getIssuer().trim());
        cert.setIssueDate(trimOrNull(dto.getIssueDate()));
        cert.setCredentialId(trimOrNull(dto.getCredentialId()));
        cert.setCredentialUrl(trimOrNull(dto.getCredentialUrl()));
        cert.setImageUrl(dto.getImageUrl() != null ? dto.getImageUrl().trim() : "/images/certificates/default-cert.png");
        cert.setPdfUrl(trimOrNull(dto.getPdfUrl()));
        cert.setDescription(trimOrNull(dto.getDescription()));
        cert.setDisplayOrder(dto.getDisplayOrder());
    }

    private String trimOrNull(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
