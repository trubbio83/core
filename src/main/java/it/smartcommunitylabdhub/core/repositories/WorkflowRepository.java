package it.smartcommunitylabdhub.core.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import it.smartcommunitylabdhub.core.models.entities.Workflow;

public interface WorkflowRepository extends JpaRepository<Workflow, String> {

    List<Workflow> findByProject(String project);

    Page<Workflow> findAll(Pageable pageable);
}
