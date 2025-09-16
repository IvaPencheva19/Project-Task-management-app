package project.management.project_management.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "workspace_members")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceMember {

    @EmbeddedId
    private WorkspaceMemberId id = new WorkspaceMemberId();

    @ManyToOne
    @MapsId("workspaceId")
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.MEMBER;

    public enum Role {
        OWNER, MEMBER
    }
}

