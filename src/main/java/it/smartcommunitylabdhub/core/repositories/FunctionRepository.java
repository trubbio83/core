package it.smartcommunitylabdhub.core.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import it.smartcommunitylabdhub.core.models.Function;

public interface FunctionRepository extends JpaRepository<Function, UUID> {

    List<Function> findByProject(String project);

}
