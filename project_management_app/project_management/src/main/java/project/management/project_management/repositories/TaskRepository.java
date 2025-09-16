package project.management.project_management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.management.project_management.entities.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}

