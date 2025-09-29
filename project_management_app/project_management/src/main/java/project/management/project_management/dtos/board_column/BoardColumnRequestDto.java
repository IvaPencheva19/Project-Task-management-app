package project.management.project_management.dtos.board_column;

import lombok.Data;

@Data
public class BoardColumnRequestDto {
    private String name;
    private Integer position;
}