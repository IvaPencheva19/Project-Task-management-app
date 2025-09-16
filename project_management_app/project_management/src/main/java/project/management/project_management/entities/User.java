package project.management.project_management.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @OneToMany(mappedBy = "owner")
    private Set<Workspace> ownedWorkspaces = new HashSet<>();

    @OneToMany(mappedBy = "assignee")
    private Set<Task> assignedTasks = new HashSet<>();
}


