package it.smartcommunitylabdhub.core.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.smartcommunitylabdhub.core.models.Workflow;

public interface WorkflowRepository extends JpaRepository<Workflow, String> {

    List<Workflow> findByProject(String project);

}
