package it.smartcommunitylabdhub.core.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import it.smartcommunitylabdhub.core.models.Function;

public interface FunctionRepository extends JpaRepository<Function, String> {

    List<Function> findByProject(String project);

    Page<Function> findAll(Pageable pageable);
}
