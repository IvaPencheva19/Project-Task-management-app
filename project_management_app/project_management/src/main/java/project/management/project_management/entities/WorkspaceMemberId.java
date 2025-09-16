package project.management.project_management.entities;


import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceMemberId implements Serializable {
    private Long workspaceId;
    private Long userId;

    @Override
    public int hashCode() {
        return Objects.hash(workspaceId, userId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof WorkspaceMemberId)) return false;
        WorkspaceMemberId that = (WorkspaceMemberId) obj;
        return Objects.equals(workspaceId, that.workspaceId) &&
                Objects.equals(userId, that.userId);
    }
}
