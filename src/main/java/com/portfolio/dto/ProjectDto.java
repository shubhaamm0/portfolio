package com.portfolio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProjectDto {

    private Long id;

    @NotBlank(message = "Project title is required")
    @Size(max = 150, message = "Title must be at most 150 characters")
    private String title;

    @Size(max = 180, message = "Slug must be at most 180 characters")
    private String slug;

    @NotBlank(message = "Short description is required")
    @Size(max = 500, message = "Short description must be at most 500 characters")
    private String shortDescription;

    @NotBlank(message = "Full description is required")
    private String fullDescription;

    @NotBlank(message = "Tech stack is required")
    @Size(max = 500, message = "Tech stack must be at most 500 characters")
    private String techStack;

    private String highlights;

    @Size(max = 80, message = "Duration label must be at most 80 characters")
    private String durationLabel;

    @Size(max = 300, message = "GitHub URL must be at most 300 characters")
    private String githubUrl;

    @Size(max = 300, message = "Live URL must be at most 300 characters")
    private String liveUrl;

    @Size(max = 300, message = "Swagger URL must be at most 300 characters")
    private String swaggerUrl;

    @Size(max = 300, message = "Image URL must be at most 300 characters")
    private String imageUrl;

    private boolean featured = true;

    private int displayOrder = 0;

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
}
