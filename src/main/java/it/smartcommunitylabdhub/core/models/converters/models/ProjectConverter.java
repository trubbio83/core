package it.smartcommunitylabdhub.core.models.converters.models;

import org.springframework.stereotype.Component;

import it.smartcommunitylabdhub.core.exception.CustomException;
import it.smartcommunitylabdhub.core.models.Project;
import it.smartcommunitylabdhub.core.models.converters.interfaces.Converter;
import it.smartcommunitylabdhub.core.models.dtos.ProjectDTO;

@Component
public class ProjectConverter implements Converter<Project, ProjectDTO> {

    @Override
    public ProjectDTO convert(Project project) throws CustomException {
        return ProjectDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .build();
    }

    @Override
    public Project reverseConvert(ProjectDTO projectDTO) throws CustomException {
        return Project.builder().name(projectDTO.getName()).build();
    }

}
