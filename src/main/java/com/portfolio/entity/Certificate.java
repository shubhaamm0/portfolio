package com.portfolio.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "certificates")
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 180)
    private String title;

    @Column(nullable = false, length = 150)
    private String issuer;

    @Column(name = "issue_date", length = 80)
    private String issueDate;

    @Column(name = "credential_id", length = 150)
    private String credentialId;

    @Column(name = "credential_url", length = 300)
    private String credentialUrl;

    @Column(name = "image_url", nullable = false, length = 300)
    private String imageUrl;

    @Column(name = "pdf_url", length = 300)
    private String pdfUrl;

    @Column(length = 600)
    private String description;

    @Column(name = "display_order", nullable = false)
    private int displayOrder = 0;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

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

    public LocalDateTime getCreatedAt() { return createdAt; }
}
