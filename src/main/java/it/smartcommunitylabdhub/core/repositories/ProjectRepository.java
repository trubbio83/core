package it.smartcommunitylabdhub.core.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import it.smartcommunitylabdhub.core.models.Project;

public interface ProjectRepository extends JpaRepository<Project, String> {

    Optional<Project> findByName(String name);
}
