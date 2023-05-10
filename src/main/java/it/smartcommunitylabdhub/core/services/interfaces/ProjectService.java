package it.smartcommunitylabdhub.core.services.interfaces;

import java.util.List;

import it.smartcommunitylabdhub.core.models.dtos.ArtifactDTO;
import it.smartcommunitylabdhub.core.models.dtos.FunctionDTO;
import it.smartcommunitylabdhub.core.models.dtos.ProjectDTO;
import it.smartcommunitylabdhub.core.models.dtos.WorkflowDTO;

public interface ProjectService {

    List<ProjectDTO> getProjects();

    ProjectDTO createProject(ProjectDTO projectDTO);

    ProjectDTO getProject(String name);

    ProjectDTO updateProject(String name);

    void deleteProject(String name);

    List<FunctionDTO> getProjectFunctions(String name);

    List<ArtifactDTO> getProjectArtifacts(String name);

    List<WorkflowDTO> getProjectWorkflows(String name);
}
