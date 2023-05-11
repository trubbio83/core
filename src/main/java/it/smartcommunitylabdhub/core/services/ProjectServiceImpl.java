package it.smartcommunitylabdhub.core.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import it.smartcommunitylabdhub.core.exception.CoreException;
import it.smartcommunitylabdhub.core.exception.CustomException;
import it.smartcommunitylabdhub.core.models.Artifact;
import it.smartcommunitylabdhub.core.models.Function;
import it.smartcommunitylabdhub.core.models.Project;
import it.smartcommunitylabdhub.core.models.Workflow;
import it.smartcommunitylabdhub.core.models.converters.CommandFactory;
import it.smartcommunitylabdhub.core.models.dtos.ArtifactDTO;
import it.smartcommunitylabdhub.core.models.dtos.FunctionDTO;
import it.smartcommunitylabdhub.core.models.dtos.ProjectDTO;
import it.smartcommunitylabdhub.core.models.dtos.WorkflowDTO;
import it.smartcommunitylabdhub.core.repositories.ArtifactRepository;
import it.smartcommunitylabdhub.core.repositories.FunctionRepository;
import it.smartcommunitylabdhub.core.repositories.ProjectRepository;
import it.smartcommunitylabdhub.core.repositories.WorkflowRepository;
import it.smartcommunitylabdhub.core.services.builders.daos.ProjectBuilder;
import it.smartcommunitylabdhub.core.services.builders.dtos.ProjectDTOBuilder;
import it.smartcommunitylabdhub.core.services.interfaces.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final FunctionRepository functionRepository;
    private final ArtifactRepository artifactRepository;
    private final WorkflowRepository workflowRepository;
    private final CommandFactory commandFactory;

    public ProjectServiceImpl(
            ProjectRepository projectRepository, FunctionRepository functionRepository,
            ArtifactRepository artifactRepository, WorkflowRepository workflowRepository,
            CommandFactory commandFactory) {
        this.projectRepository = projectRepository;
        this.functionRepository = functionRepository;
        this.artifactRepository = artifactRepository;
        this.workflowRepository = workflowRepository;
        this.commandFactory = commandFactory;

    }

    @Override
    public ProjectDTO getProject(String uuid) {

        Project project = projectRepository.findById(uuid).orElse(null);
        if (project == null) {
            throw new CoreException(
                    "project-not-found",
                    "The project you are searching for does not exist.",
                    HttpStatus.NOT_FOUND);
        }

        try {
            List<Function> functions = functionRepository.findByProject(project.getName());
            List<Artifact> artifacts = artifactRepository.findByProject(project.getName());
            List<Workflow> workflows = workflowRepository.findByProject(project.getName());

            return new ProjectDTOBuilder(commandFactory, project, artifacts, functions, workflows).build();

        } catch (CustomException e) {
            throw new CoreException(
                    "internal-server-error",
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<ProjectDTO> getProjects(Pageable pageable) {
        try {
            Page<Project> projectPage = this.projectRepository.findAll(pageable);
            return projectPage.getContent().stream().map((project) -> {
                List<Function> functions = functionRepository.findByProject(project.getName());
                List<Artifact> artifacts = artifactRepository.findByProject(project.getName());
                List<Workflow> workflows = workflowRepository.findByProject(project.getName());

                return new ProjectDTOBuilder(commandFactory, project, artifacts, functions, workflows).build();
            }).collect(Collectors.toList());
        } catch (CustomException e) {
            throw new CoreException(
                    "internal-server-error",
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        try {
            // Build a project and store it on db
            Project project = new ProjectBuilder(commandFactory, projectDTO).build();
            this.projectRepository.save(project);

            // Return project DTO
            return new ProjectDTOBuilder(commandFactory, project, List.of(), List.of(), List.of()).build();

        } catch (CustomException e) {
            throw new CoreException(
                    "internal-server-error",
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ProjectDTO updateProject(String uuid) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateProject'");
    }

    @Override
    public void deleteProject(String uuid) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteProject'");
    }

    @Override
    public List<FunctionDTO> getProjectFunctions(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProjectFunctions'");
    }

    @Override
    public List<ArtifactDTO> getProjectArtifacts(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProjectArtifacts'");
    }

    @Override
    public List<WorkflowDTO> getProjectWorkflows(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProjectWorkflows'");
    }

}
