package it.smartcommunitylabdhub.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import it.smartcommunitylabdhub.core.models.Run;

public interface RunRepository extends JpaRepository<Run, String> {

    List<Run> findByProject(String uuid);

    List<Run> findByName(String name);
}
