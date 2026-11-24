package com.portfolio.controller;

import com.portfolio.entity.Project;
import com.portfolio.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    public String listProjects(Model model) {
        model.addAttribute("projects", projectService.findAllProjects());
        model.addAttribute("projectService", projectService);
        return "pages/projects";
    }

    @GetMapping("/projects/{slug}")
    public String projectDetail(@PathVariable String slug, Model model) {
        Project project = projectService.findBySlug(slug);
        model.addAttribute("project", project);
        model.addAttribute("techList", projectService.parseTechList(project.getTechStack()));
        model.addAttribute("highlights", projectService.parseHighlightLines(project.getHighlights()));
        return "pages/project-detail";
    }

    @GetMapping("/api/projects")
    @ResponseBody
    public ResponseEntity<List<Project>> getAllProjectsJson() {
        return ResponseEntity.ok(projectService.findAllProjects());
    }

    @GetMapping("/api/projects/{id}")
    @ResponseBody
    public ResponseEntity<Project> getProjectByIdJson(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.findById(id));
    }
}
