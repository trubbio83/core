package it.smartcommunitylabdhub.core.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import it.smartcommunitylabdhub.core.exception.CustomException;
import it.smartcommunitylabdhub.core.models.Artifact;
import it.smartcommunitylabdhub.core.models.Function;
import it.smartcommunitylabdhub.core.models.Project;
import it.smartcommunitylabdhub.core.models.Workflow;
import it.smartcommunitylabdhub.core.models.converters.fields.CBORConverter;
import it.smartcommunitylabdhub.core.models.converters.interfaces.Converter;
import it.smartcommunitylabdhub.core.models.converters.models.ArtifactConverter;
import it.smartcommunitylabdhub.core.models.converters.models.FunctionConverter;
import it.smartcommunitylabdhub.core.models.converters.models.WorkflowConverter;
import it.smartcommunitylabdhub.core.models.dtos.ArtifactDTO;
import it.smartcommunitylabdhub.core.models.dtos.FunctionDTO;
import it.smartcommunitylabdhub.core.models.dtos.ProjectDTO;
import it.smartcommunitylabdhub.core.models.dtos.WorkflowDTO;
import it.smartcommunitylabdhub.core.repositories.ArtifactRepository;
import it.smartcommunitylabdhub.core.repositories.FunctionRepository;
import it.smartcommunitylabdhub.core.repositories.ProjectRepository;
import it.smartcommunitylabdhub.core.repositories.WorkflowRepository;
import it.smartcommunitylabdhub.core.services.builders.ProjectDTOBuilder;
import it.smartcommunitylabdhub.core.services.interfaces.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final FunctionRepository functionRepository;
    private final ArtifactRepository artifactRepository;
    private final WorkflowRepository workflowRepository;
    private final FunctionConverter functionConverter;
    private final ArtifactConverter artifactConverter;
    private final WorkflowConverter workflowConverter;
    private final CBORConverter cborConverter;

    public ProjectServiceImpl(
            ProjectRepository projectRepository, FunctionRepository functionRepository,
            ArtifactRepository artifactRepository, WorkflowRepository workflowRepository,
            FunctionConverter functionConverter, ArtifactConverter artifactConverter,
            WorkflowConverter workflowConverter, CBORConverter cborConverter) {
        this.projectRepository = projectRepository;
        this.functionRepository = functionRepository;
        this.artifactRepository = artifactRepository;
        this.workflowRepository = workflowRepository;
        this.functionConverter = functionConverter;
        this.artifactConverter = artifactConverter;
        this.workflowConverter = workflowConverter;
        this.cborConverter = cborConverter;

    }

    @Override
    public ProjectDTO getProject(String name) {

        Project project = projectRepository.findByName(name).orElse(null);
        if (project == null) {
            // TODO: Handle project not found
            return null;
        }

        List<Function> functions = functionRepository.findByProject(project.getName());
        List<Artifact> artifacts = artifactRepository.findByProject(project.getName());
        List<Workflow> workflows = workflowRepository.findByProject(project.getName());

        Optional<ProjectDTO> projectDTO;

        try {
            projectDTO = Optional.of(new ProjectDTOBuilder()
                    .setId(project.getId())
                    .setName(project.getName())
                    .setDescription(project.getDescription())
                    .setSource(project.getSource())
                    .setExtra(cborConverter.convertReverse(project.getExtra()))
                    .setState(project.getState().name())
                    .setFunctions(convertEntities(functions, functionConverter))
                    .setArtifacts(convertEntities(artifacts, artifactConverter))
                    .setWorkflows(convertEntities(workflows, workflowConverter))
                    .build());
        } catch (CustomException e) {
            projectDTO = Optional.empty();
        }

        return projectDTO.orElse(new ProjectDTO());
    }

    @Override
    public List<ProjectDTO> getProjects() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProjects'");
    }

    @Override
    public ProjectDTO createProject() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createProject'");
    }

    @Override
    public ProjectDTO updateProject(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateProject'");
    }

    @Override
    public void deleteProject(String name) {
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

    /**
     * Map entities DAO into the corresponding DTO
     * 
     * @param <T>
     * @param <U>
     * @param entities
     * @param converter
     * @return
     */
    private <T, U> List<U> convertEntities(List<T> entities, Converter<T, U> converter) {
        return entities.stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }

}
