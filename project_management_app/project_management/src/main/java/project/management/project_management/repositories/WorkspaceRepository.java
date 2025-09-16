package project.management.project_management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.management.project_management.entities.Workspace;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
}

