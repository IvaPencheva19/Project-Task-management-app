package project.management.project_management.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.management.project_management.dtos.board_column.BoardColumnDto;
import project.management.project_management.dtos.board_column.BoardColumnRequestDto;
import project.management.project_management.services.BoardColumnService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardColumnController {

    private final BoardColumnService columnService;

    @PostMapping("/api/projects/{projectId}/columns")
    public ResponseEntity<BoardColumnDto> createColumn(@PathVariable Long projectId,
                                                       @RequestBody BoardColumnRequestDto request) {
        return ResponseEntity.ok(columnService.createColumn(projectId, request));
    }

    @GetMapping("/api/projects/{projectId}/columns")
    public ResponseEntity<List<BoardColumnDto>> getColumns(@PathVariable Long projectId) {
        return ResponseEntity.ok(columnService.getColumns(projectId));
    }

    @PutMapping("/api/columns/{columnId}")
    public ResponseEntity<BoardColumnDto> updateColumn(@PathVariable Long columnId,
                                                  @RequestBody BoardColumnRequestDto request) {
        return ResponseEntity.ok(columnService.updateColumn(columnId, request));
    }
}
