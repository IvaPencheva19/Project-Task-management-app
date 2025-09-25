package project.management.project_management.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.management.project_management.dtos.project.ProjectDto;
import project.management.project_management.services.ProjectService;

import java.util.List;


@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("/my")
    public ResponseEntity<List<ProjectDto>> getAllProjectsForUser() {
        return ResponseEntity.ok(projectService.getAllProjectsForUser());
    }
}
