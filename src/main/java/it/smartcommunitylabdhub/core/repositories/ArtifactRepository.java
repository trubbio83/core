package it.smartcommunitylabdhub.core.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.smartcommunitylabdhub.core.models.entities.Artifact;

public interface ArtifactRepository extends JpaRepository<Artifact, String> {
    List<Artifact> findByProject(String project);
}
