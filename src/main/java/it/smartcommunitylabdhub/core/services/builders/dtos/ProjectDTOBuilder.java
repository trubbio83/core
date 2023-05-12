package it.smartcommunitylabdhub.core.services.builders.dtos;

import java.util.List;

import it.smartcommunitylabdhub.core.models.Artifact;
import it.smartcommunitylabdhub.core.models.Function;
import it.smartcommunitylabdhub.core.models.Project;
import it.smartcommunitylabdhub.core.models.Workflow;
import it.smartcommunitylabdhub.core.models.converters.CommandFactory;
import it.smartcommunitylabdhub.core.models.converters.ConversionUtils;
import it.smartcommunitylabdhub.core.models.dtos.ArtifactDTO;
import it.smartcommunitylabdhub.core.models.dtos.FunctionDTO;
import it.smartcommunitylabdhub.core.models.dtos.ProjectDTO;
import it.smartcommunitylabdhub.core.models.dtos.WorkflowDTO;
import it.smartcommunitylabdhub.core.models.enums.State;
import it.smartcommunitylabdhub.core.services.factory.EntityFactory;

public class ProjectDTOBuilder {

        private CommandFactory commandFactory;
        private Project project;
        private List<Artifact> artifacts;
        private List<Function> functions;
        private List<Workflow> workflows;

        public ProjectDTOBuilder(
                        CommandFactory commandFactory,
                        Project project,
                        List<Artifact> artifacts,
                        List<Function> functions,
                        List<Workflow> workflows) {
                this.project = project;
                this.commandFactory = commandFactory;
                this.functions = functions;
                this.workflows = workflows;
                this.artifacts = artifacts;
        }

        public ProjectDTO build() {
                return EntityFactory.create(ProjectDTO::new, project, builder -> {
                        builder
                                        .with(dto -> dto.setId(project.getId()))
                                        .with(dto -> dto.setName(project.getName()))
                                        .with(dto -> dto.setDescription(project.getDescription()))
                                        .with(dto -> dto.setSource(project.getSource()))
                                        .with(dto -> dto.setState(project.getState() == null ? State.CREATED.name()
                                                        : project.getState().name()))
                                        .with(dto -> dto.setExtra(ConversionUtils.reverse(
                                                        project.getExtra(),
                                                        commandFactory,
                                                        "cbor")))

                                        .with(dto -> dto.setFunctions(
                                                        (List<FunctionDTO>) ConversionUtils.reverseIterable(
                                                                        functions,
                                                                        commandFactory,
                                                                        "function", FunctionDTO.class)))
                                        .with(dto -> dto.setArtifacts(
                                                        (List<ArtifactDTO>) ConversionUtils.reverseIterable(
                                                                        artifacts,
                                                                        commandFactory,
                                                                        "artifact", ArtifactDTO.class)))
                                        .with(dto -> dto.setWorkflows(
                                                        (List<WorkflowDTO>) ConversionUtils.reverseIterable(
                                                                        workflows,
                                                                        commandFactory,
                                                                        "workflow", WorkflowDTO.class)))
                                        .with(dto -> dto.setCreated(project.getCreated()))
                                        .with(dto -> dto.setUpdated(project.getUpdated()));

                });
        }
}