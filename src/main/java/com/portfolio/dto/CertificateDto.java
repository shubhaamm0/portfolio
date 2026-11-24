package com.portfolio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CertificateDto {

    private Long id;

    @NotBlank(message = "Certificate title is required")
    @Size(max = 180, message = "Title must be at most 180 characters")
    private String title;

    @NotBlank(message = "Issuing organization is required")
    @Size(max = 150, message = "Issuer must be at most 150 characters")
    private String issuer;

    @Size(max = 80, message = "Issue date must be at most 80 characters")
    private String issueDate;

    @Size(max = 150, message = "Credential ID must be at most 150 characters")
    private String credentialId;

    @Size(max = 300, message = "Credential URL must be at most 300 characters")
    private String credentialUrl;

    @Size(max = 300, message = "Image URL must be at most 300 characters")
    private String imageUrl;

    @Size(max = 300, message = "PDF URL must be at most 300 characters")
    private String pdfUrl;

    @Size(max = 600, message = "Description must be at most 600 characters")
    private String description;

    private int displayOrder = 0;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getIssuer() { return issuer; }
    public void setIssuer(String issuer) { this.issuer = issuer; }

    public String getIssueDate() { return issueDate; }
    public void setIssueDate(String issueDate) { this.issueDate = issueDate; }

    public String getCredentialId() { return credentialId; }
    public void setCredentialId(String credentialId) { this.credentialId = credentialId; }

    public String getCredentialUrl() { return credentialUrl; }
    public void setCredentialUrl(String credentialUrl) { this.credentialUrl = credentialUrl; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getPdfUrl() { return pdfUrl; }
    public void setPdfUrl(String pdfUrl) { this.pdfUrl = pdfUrl; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(int displayOrder) { this.displayOrder = displayOrder; }
}
