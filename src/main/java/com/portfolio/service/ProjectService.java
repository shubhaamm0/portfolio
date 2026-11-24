package com.portfolio.service;

import com.portfolio.dto.ProjectDto;
import com.portfolio.entity.Project;
import com.portfolio.exception.ResourceNotFoundException;
import com.portfolio.repository.ProjectRepository;
import com.portfolio.util.SlugUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Transactional(readOnly = true)
    public List<Project> findAllProjects() {
        return projectRepository.findAllByOrderByDisplayOrderAscCreatedAtDesc();
    }

    @Transactional(readOnly = true)
    public List<Project> findFeaturedProjects() {
        return projectRepository.findByFeaturedTrueOrderByDisplayOrderAscCreatedAtDesc();
    }

    @Transactional(readOnly = true)
    public Project findById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Project findBySlug(String slug) {
        return projectRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found: " + slug));
    }

    @Transactional
    public Project createProject(ProjectDto dto) {
        Project project = new Project();
        mapDtoToEntity(dto, project, null);
        return projectRepository.save(project);
    }

    @Transactional
    public Project updateProject(Long id, ProjectDto dto) {
        Project existing = findById(id);
        mapDtoToEntity(dto, existing, id);
        return projectRepository.save(existing);
    }

    @Transactional
    public void deleteProject(Long id) {
        Project existing = findById(id);
        projectRepository.delete(existing);
    }

    public ProjectDto toDto(Project project) {
        ProjectDto dto = new ProjectDto();
        dto.setId(project.getId());
        dto.setTitle(project.getTitle());
        dto.setSlug(project.getSlug());
        dto.setShortDescription(project.getShortDescription());
        dto.setFullDescription(project.getFullDescription());
        dto.setTechStack(project.getTechStack());
        dto.setHighlights(project.getHighlights());
        dto.setDurationLabel(project.getDurationLabel());
        dto.setGithubUrl(project.getGithubUrl());
        dto.setLiveUrl(project.getLiveUrl());
        dto.setSwaggerUrl(project.getSwaggerUrl());
        dto.setImageUrl(project.getImageUrl());
        dto.setFeatured(project.isFeatured());
        dto.setDisplayOrder(project.getDisplayOrder());
        return dto;
    }

    public List<String> parseTechList(String techStack) {
        if (techStack == null || techStack.isBlank()) {
            return Collections.emptyList();
        }
        return Arrays.stream(techStack.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }

    public List<String> parseHighlightLines(String highlights) {
        if (highlights == null || highlights.isBlank()) {
            return Collections.emptyList();
        }
        return Arrays.stream(highlights.split("\\r?\\n"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }

    private void mapDtoToEntity(ProjectDto dto, Project project, Long currentId) {
        project.setTitle(dto.getTitle().trim());
        String baseSlug = (dto.getSlug() != null && !dto.getSlug().isBlank())
                ? SlugUtil.toSlug(dto.getSlug())
                : SlugUtil.toSlug(dto.getTitle());
        project.setSlug(ensureUniqueSlug(baseSlug, currentId));
        project.setShortDescription(dto.getShortDescription().trim());
        project.setFullDescription(dto.getFullDescription().trim());
        project.setTechStack(dto.getTechStack().trim());
        project.setHighlights(trimOrNull(dto.getHighlights()));
        project.setDurationLabel(trimOrNull(dto.getDurationLabel()));
        project.setGithubUrl(trimOrNull(dto.getGithubUrl()));
        project.setLiveUrl(trimOrNull(dto.getLiveUrl()));
        project.setSwaggerUrl(trimOrNull(dto.getSwaggerUrl()));
        project.setImageUrl(trimOrNull(dto.getImageUrl()));
        project.setFeatured(dto.isFeatured());
        project.setDisplayOrder(dto.getDisplayOrder());
    }

    private String ensureUniqueSlug(String baseSlug, Long currentId) {
        String candidate = baseSlug;
        int counter = 2;
        while (currentId == null
                ? projectRepository.existsBySlug(candidate)
                : projectRepository.existsBySlugAndIdNot(candidate, currentId)) {
            candidate = baseSlug + "-" + counter++;
        }
        return candidate;
    }

    private String trimOrNull(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
