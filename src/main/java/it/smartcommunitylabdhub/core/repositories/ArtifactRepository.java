package it.smartcommunitylabdhub.core.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import it.smartcommunitylabdhub.core.models.Artifact;

public interface ArtifactRepository extends JpaRepository<Artifact, UUID> {
    List<Artifact> findByProject(String project);
}
