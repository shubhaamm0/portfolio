package com.portfolio.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, unique = true, length = 180)
    private String slug;

    @Column(name = "short_description", nullable = false, length = 500)
    private String shortDescription;

    @Column(name = "full_description", nullable = false, columnDefinition = "TEXT")
    private String fullDescription;

    @Column(name = "tech_stack", nullable = false, length = 500)
    private String techStack;

    @Column(columnDefinition = "TEXT")
    private String highlights;

    @Column(name = "duration_label", length = 80)
    private String durationLabel;

    @Column(name = "github_url", length = 300)
    private String githubUrl;

    @Column(name = "live_url", length = 300)
    private String liveUrl;

    @Column(name = "swagger_url", length = 300)
    private String swaggerUrl;

    @Column(name = "image_url", length = 300)
    private String imageUrl;

    @Column(nullable = false)
    private boolean featured = true;

    @Column(name = "display_order", nullable = false)
    private int displayOrder = 0;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getShortDescription() { return shortDescription; }
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }

    public String getFullDescription() { return fullDescription; }
    public void setFullDescription(String fullDescription) { this.fullDescription = fullDescription; }

    public String getTechStack() { return techStack; }
    public void setTechStack(String techStack) { this.techStack = techStack; }

    public String getHighlights() { return highlights; }
    public void setHighlights(String highlights) { this.highlights = highlights; }

    public String getDurationLabel() { return durationLabel; }
    public void setDurationLabel(String durationLabel) { this.durationLabel = durationLabel; }

    public String getGithubUrl() { return githubUrl; }
    public void setGithubUrl(String githubUrl) { this.githubUrl = githubUrl; }

    public String getLiveUrl() { return liveUrl; }
    public void setLiveUrl(String liveUrl) { this.liveUrl = liveUrl; }

    public String getSwaggerUrl() { return swaggerUrl; }
    public void setSwaggerUrl(String swaggerUrl) { this.swaggerUrl = swaggerUrl; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public boolean isFeatured() { return featured; }
    public void setFeatured(boolean featured) { this.featured = featured; }

    public int getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(int displayOrder) { this.displayOrder = displayOrder; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
