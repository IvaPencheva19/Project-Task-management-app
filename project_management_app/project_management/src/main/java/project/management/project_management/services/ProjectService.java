package project.management.project_management.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.management.project_management.dtos.project.ProjectDto;
import project.management.project_management.dtos.project.ProjectRequestDto;
import project.management.project_management.entities.Project;
import project.management.project_management.entities.User;
import project.management.project_management.entities.Workspace;
import project.management.project_management.entities.WorkspaceMember;
import project.management.project_management.repositories.ProjectRepository;
import project.management.project_management.repositories.WorkspaceMemberRepository;
import project.management.project_management.repositories.WorkspaceRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final UserService userService;
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final ProjectRepository projectRepository;

    public ProjectDto createProject(Long workspaceId, ProjectRequestDto request) {
        User currentUser = userService.getCurrentUser();

        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new RuntimeException("Workspace not found"));

        WorkspaceMember member = workspaceMemberRepository.findByWorkspaceIdAndUserId(workspaceId, currentUser.getId());
        if (member == null) {
            throw new RuntimeException("You are not a member of this workspace");
        }

        Project project = new Project();
        project.setName(request.getName());
        project.setWorkspace(workspace);

        project = projectRepository.save(project);

        ProjectDto dto = new ProjectDto();
        dto.setId(project.getId());
        dto.setName(project.getName());
        return dto;
    }

    public List<ProjectDto> getProjects(Long workspaceId) {
        User currentUser = userService.getCurrentUser();

        WorkspaceMember member = workspaceMemberRepository.findByWorkspaceIdAndUserId(workspaceId, currentUser.getId());
        if (member == null) {
            throw new RuntimeException("You are not a member of this workspace");
        }

        return projectRepository.findByWorkspaceId(workspaceId)
                .stream()
                .map(p -> {
                    ProjectDto dto = new ProjectDto();
                    dto.setId(p.getId());
                    dto.setName(p.getName());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<ProjectDto> getAllProjectsForUser() {
        User currentUser = userService.getCurrentUser();

        List<WorkspaceMember> memberships = workspaceMemberRepository.findByUserId(currentUser.getId());

        return memberships.stream()
                .flatMap(m -> projectRepository.findByWorkspaceId(m.getWorkspace().getId()).stream())
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ProjectDto updateProjectName(Long projectId, ProjectRequestDto request) {
        User currentUser = userService.getCurrentUser();

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        WorkspaceMember member = workspaceMemberRepository
                .findByWorkspaceIdAndUserId(project.getWorkspace().getId(), currentUser.getId());
        if (member == null) {
            throw new RuntimeException("You are not a member of this workspace");
        }

        project.setName(request.getName());
        projectRepository.save(project);

        ProjectDto dto = new ProjectDto();
        dto.setId(project.getId());
        dto.setName(project.getName());
        return dto;
    }


    private ProjectDto toDto(Project project) {
        ProjectDto dto = new ProjectDto();
        dto.setId(project.getId());
        dto.setName(project.getName());
        return dto;
    }
}

