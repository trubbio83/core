package it.smartcommunitylabdhub.core.services.interfaces;

import java.util.List;

import org.springframework.data.domain.Pageable;

import it.smartcommunitylabdhub.core.models.dtos.ArtifactDTO;
import it.smartcommunitylabdhub.core.models.dtos.FunctionDTO;
import it.smartcommunitylabdhub.core.models.dtos.ProjectDTO;
import it.smartcommunitylabdhub.core.models.dtos.WorkflowDTO;

public interface ProjectService {

    List<ProjectDTO> getProjects(Pageable pageable);

    ProjectDTO createProject(ProjectDTO projectDTO);

    ProjectDTO getProject(String uuid);

    ProjectDTO updateProject(ProjectDTO projectDTO, String uuid);

    void deleteProject(String uuid);

    List<FunctionDTO> getProjectFunctions(String name);

    List<ArtifactDTO> getProjectArtifacts(String name);

    List<WorkflowDTO> getProjectWorkflows(String name);
}
