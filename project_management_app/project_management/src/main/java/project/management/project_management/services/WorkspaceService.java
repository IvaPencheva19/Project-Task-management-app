package project.management.project_management.services;



import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.management.project_management.dtos.workspace.AddMemberRequestDto;
import project.management.project_management.dtos.workspace.WorkspaceDto;
import project.management.project_management.dtos.workspace.WorkspaceMemberDto;
import project.management.project_management.dtos.workspace.WorkspaceRequestDto;
import project.management.project_management.entities.User;
import project.management.project_management.entities.Workspace;
import project.management.project_management.entities.WorkspaceMember;
import project.management.project_management.entities.WorkspaceMemberId;
import project.management.project_management.repositories.UserRepository;
import project.management.project_management.repositories.WorkspaceMemberRepository;
import project.management.project_management.repositories.WorkspaceRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkspaceService {

    private final UserService userService;
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final UserRepository userRepository;


    public WorkspaceDto createWorkspace(WorkspaceRequestDto request) {
        User currentUser = userService.getCurrentUser();

        Workspace workspace = new Workspace();
        workspace.setName(request.getName());
        workspace = workspaceRepository.save(workspace);

        WorkspaceMember member = new WorkspaceMember();
        member.setId(new WorkspaceMemberId(workspace.getId(), currentUser.getId()));
        member.setWorkspace(workspace);
        member.setUser(currentUser);
        member.setRole(WorkspaceMember.Role.OWNER);
        workspaceMemberRepository.save(member);

        WorkspaceDto dto = new WorkspaceDto();
        dto.setId(workspace.getId());
        dto.setName(workspace.getName());
        return dto;
    }

    public List<WorkspaceDto> getUserWorkspaces() {
        User currentUser = userService.getCurrentUser();

        return workspaceMemberRepository.findByUserId(currentUser.getId())
                .stream()
                .map(WorkspaceMember::getWorkspace)
                .map(w -> {
                    WorkspaceDto dto = new WorkspaceDto();
                    dto.setId(w.getId());
                    dto.setName(w.getName());
                    return dto;
                })
                .collect(Collectors.toList());
    }


    public void addMember(Long workspaceId, AddMemberRequestDto request) {
        User currentUser = userService.getCurrentUser();
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new RuntimeException("Workspace not found"));

        WorkspaceMember currentMember = workspaceMemberRepository
                .findByWorkspaceIdAndUserId(workspace.getId(), currentUser.getId());
        if (currentMember == null || !WorkspaceMember.Role.OWNER.equals(currentMember.getRole())) {
            throw new RuntimeException("Only workspace owner can add members");
        }

        User userToAdd = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User with email not found"));

        WorkspaceMember existingMember = workspaceMemberRepository
                .findByWorkspaceIdAndUserId(workspace.getId(), userToAdd.getId());
        if (existingMember != null) {
            throw new RuntimeException("User is already a member of this workspace");
        }

        WorkspaceMember newMember = new WorkspaceMember();
        newMember.setId(new WorkspaceMemberId(workspace.getId(), userToAdd.getId()));
        newMember.setWorkspace(workspace);
        newMember.setUser(userToAdd);
        newMember.setRole(request.getRole() != null ? request.getRole() : WorkspaceMember.Role.MEMBER);
        workspaceMemberRepository.save(newMember);
    }


    public WorkspaceMemberDto getWorkspaceOwner(Long workspaceId) {
        WorkspaceMember owner = workspaceMemberRepository.findByWorkspaceId(workspaceId)
                .stream()
                .filter(m -> m.getRole() == WorkspaceMember.Role.OWNER)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Owner not found for workspace " + workspaceId));

        WorkspaceMemberDto dto = new WorkspaceMemberDto();
        dto.setId(owner.getUser().getId());
        dto.setUsername(owner.getUser().getUsername());
        dto.setEmail(owner.getUser().getEmail());
        dto.setRole(owner.getRole().name());
        return dto;
    }

    public List<WorkspaceMemberDto> getWorkspaceMembers(Long workspaceId) {
        return workspaceMemberRepository.findByWorkspaceId(workspaceId)
                .stream()
                .map(member -> {
                    WorkspaceMemberDto dto = new WorkspaceMemberDto();
                    dto.setId(member.getUser().getId());
                    dto.setUsername(member.getUser().getUsername());
                    dto.setEmail(member.getUser().getEmail());
                    dto.setRole(member.getRole().name());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public void removeMember(Long workspaceId, Long userIdToRemove) {
        User currentUser = userService.getCurrentUser();

        workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new RuntimeException("Workspace not found"));

        WorkspaceMember currentMember = workspaceMemberRepository
                .findByWorkspaceIdAndUserId(workspaceId, currentUser.getId());
        if (currentMember == null || currentMember.getRole() != WorkspaceMember.Role.OWNER) {
            throw new RuntimeException("Only workspace owner can remove members");
        }

        if (currentUser.getId().equals(userIdToRemove)) {
            throw new RuntimeException("Owner cannot remove themselves from workspace");
        }

        WorkspaceMember memberToRemove = workspaceMemberRepository
                .findByWorkspaceIdAndUserId(workspaceId, userIdToRemove);
        if (memberToRemove == null) {
            throw new RuntimeException("User is not a member of this workspace");
        }

        workspaceMemberRepository.deleteById(
                new WorkspaceMemberId(workspaceId, userIdToRemove)
        );
    }

    public WorkspaceDto updateWorkspaceName(Long workspaceId, WorkspaceRequestDto request) {
        User currentUser = userService.getCurrentUser();

        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new RuntimeException("Workspace not found"));

        WorkspaceMember currentMember = workspaceMemberRepository
                .findByWorkspaceIdAndUserId(workspaceId, currentUser.getId());
        if (currentMember == null || currentMember.getRole() != WorkspaceMember.Role.OWNER) {
            throw new RuntimeException("Only workspace owner can update workspace name");
        }

        workspace.setName(request.getName());
        workspaceRepository.save(workspace);

        WorkspaceDto dto = new WorkspaceDto();
        dto.setId(workspace.getId());
        dto.setName(workspace.getName());
        return dto;
    }


}
