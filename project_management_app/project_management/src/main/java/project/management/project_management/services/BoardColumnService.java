package project.management.project_management.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.management.project_management.dtos.board_column.BoardColumnDto;
import project.management.project_management.dtos.board_column.BoardColumnRequestDto;
import project.management.project_management.entities.BoardColumn;
import project.management.project_management.entities.Project;
import project.management.project_management.entities.User;
import project.management.project_management.entities.WorkspaceMember;
import project.management.project_management.repositories.BoardColumnRepository;
import project.management.project_management.repositories.ProjectRepository;
import project.management.project_management.repositories.WorkspaceMemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardColumnService {

    private final UserService userService;
    private final ProjectRepository projectRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final BoardColumnRepository boardColumnRepository;

    public BoardColumnDto createColumn(Long projectId, BoardColumnRequestDto request) {
        User currentUser = userService.getCurrentUser();

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        WorkspaceMember member = workspaceMemberRepository
                .findByWorkspaceIdAndUserId(project.getWorkspace().getId(), currentUser.getId());
        if (member == null) {
            throw new RuntimeException("You are not a member of this workspace");
        }

        BoardColumn column = new BoardColumn();
        column.setName(request.getName());
        column.setPosition(request.getPosition() != null ? request.getPosition() : 0);
        column.setProject(project);

        column = boardColumnRepository.save(column);

        return toDto(column);
    }

    public List<BoardColumnDto> getColumns(Long projectId) {
        User currentUser = userService.getCurrentUser();

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        WorkspaceMember member = workspaceMemberRepository
                .findByWorkspaceIdAndUserId(project.getWorkspace().getId(), currentUser.getId());
        if (member == null) {
            throw new RuntimeException("You are not a member of this workspace");
        }

        return boardColumnRepository.findByProjectIdOrderByPositionAsc(projectId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public BoardColumnDto updateColumn(Long columnId, BoardColumnRequestDto request) {
        User currentUser = userService.getCurrentUser();

        BoardColumn column = boardColumnRepository.findById(columnId)
                .orElseThrow(() -> new RuntimeException("Column not found"));

        WorkspaceMember member = workspaceMemberRepository
                .findByWorkspaceIdAndUserId(column.getProject().getWorkspace().getId(), currentUser.getId());
        if (member == null) {
            throw new RuntimeException("You are not a member of this workspace");
        }

        column.setName(request.getName());
        if (request.getPosition() != null) {
            column.setPosition(request.getPosition());
        }

        boardColumnRepository.save(column);
        return toDto(column);
    }

    private BoardColumnDto toDto(BoardColumn column) {
        BoardColumnDto dto = new BoardColumnDto();
        dto.setId(column.getId());
        dto.setName(column.getName());
        dto.setPosition(column.getPosition());
        return dto;
    }
}
