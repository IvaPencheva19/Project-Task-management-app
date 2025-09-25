package project.management.project_management.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.management.project_management.dtos.project.ProjectDto;
import project.management.project_management.dtos.project.ProjectRequestDto;
import project.management.project_management.services.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/api/workspaces/{workspaceId}/projects")
@RequiredArgsConstructor
public class WorkspaceProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectDto> createProject(@PathVariable Long workspaceId,
                                                    @RequestBody ProjectRequestDto request) {
        return ResponseEntity.ok(projectService.createProject(workspaceId, request));
    }

    @GetMapping
    public ResponseEntity<List<ProjectDto>> getProjects(@PathVariable Long workspaceId) {
        return ResponseEntity.ok(projectService.getProjects(workspaceId));
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectDto> updateProjectName(@PathVariable Long projectId,
                                                        @RequestBody ProjectRequestDto request) {
        return ResponseEntity.ok(projectService.updateProjectName(projectId, request));
    }

}


