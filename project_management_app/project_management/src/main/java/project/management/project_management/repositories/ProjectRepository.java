package project.management.project_management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.management.project_management.entities.Project;
import project.management.project_management.entities.User;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}

