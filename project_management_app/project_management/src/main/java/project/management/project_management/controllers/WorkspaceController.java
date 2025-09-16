package project.management.project_management.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.management.project_management.dtos.workspace.AddMemberRequestDto;
import project.management.project_management.dtos.workspace.WorkspaceDto;
import project.management.project_management.dtos.workspace.WorkspaceMemberDto;
import project.management.project_management.dtos.workspace.WorkspaceRequestDto;
import project.management.project_management.services.WorkspaceService;

import java.util.List;

@RestController
@RequestMapping("/api/workspaces")
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @PostMapping
    public ResponseEntity<WorkspaceDto> createWorkspace(@RequestBody WorkspaceRequestDto request) {
        return ResponseEntity.ok(workspaceService.createWorkspace(request));
    }
    @PutMapping("/{workspaceId}")
    public ResponseEntity<WorkspaceDto> updateWorkspaceName(
            @PathVariable Long workspaceId,
            @RequestBody WorkspaceRequestDto request) {
        return ResponseEntity.ok(workspaceService.updateWorkspaceName(workspaceId, request));
    }

    @GetMapping
    public ResponseEntity<List<WorkspaceDto>> getUserWorkspaces() {
        return ResponseEntity.ok(workspaceService.getUserWorkspaces());
    }

    @GetMapping("/{workspaceId}/owner")
    public ResponseEntity<WorkspaceMemberDto> getWorkspaceOwner(@PathVariable Long workspaceId) {
        return ResponseEntity.ok(workspaceService.getWorkspaceOwner(workspaceId));
    }

    @PostMapping("/{workspaceId}/members")
    public ResponseEntity<String> addMember(@PathVariable Long workspaceId,
                                            @RequestBody AddMemberRequestDto request) {
        workspaceService.addMember(workspaceId, request);
        return ResponseEntity.ok("Member added successfully");
    }

    @GetMapping("/{workspaceId}/members")
    public ResponseEntity<List<WorkspaceMemberDto>> getWorkspaceMembers(@PathVariable Long workspaceId) {
        return ResponseEntity.ok(workspaceService.getWorkspaceMembers(workspaceId));
    }


    @DeleteMapping("/{workspaceId}/members/{userId}")
    public ResponseEntity<String> removeMember(@PathVariable Long workspaceId,
                                               @PathVariable Long userId) {
        workspaceService.removeMember(workspaceId, userId);
        return ResponseEntity.ok("Member removed successfully");
    }
}