package project.management.project_management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.management.project_management.entities.WorkspaceMember;
import project.management.project_management.entities.WorkspaceMemberId;

import java.util.List;

@Repository
public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMember, WorkspaceMemberId> {
    List<WorkspaceMember> findByWorkspaceId(Long workspaceId);

    List<WorkspaceMember> findByUserId(Long userId);

    WorkspaceMember findByWorkspaceIdAndUserId(Long workspaceId, Long userId);
    void deleteAllByWorkspaceId(Long workspaceId);
}

