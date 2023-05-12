package it.smartcommunitylabdhub.core.services.builders.daos;

import it.smartcommunitylabdhub.core.models.Project;
import it.smartcommunitylabdhub.core.models.converters.CommandFactory;
import it.smartcommunitylabdhub.core.models.converters.ConversionUtils;
import it.smartcommunitylabdhub.core.models.dtos.ProjectDTO;
import it.smartcommunitylabdhub.core.models.enums.State;
import it.smartcommunitylabdhub.core.services.factory.EntityFactory;

public class ProjectBuilder {

        private CommandFactory commandFactory;
        private ProjectDTO projectDTO;

        public ProjectBuilder(
                        CommandFactory commandFactory,
                        ProjectDTO projectDTO) {
                this.projectDTO = projectDTO;
                this.commandFactory = commandFactory;
        }

        /**
         * Build a project from a projectDTO and store extra values as a cbor
         * 
         * @return
         */
        public Project build() {
                Project project = EntityFactory.combine(
                                ConversionUtils.convert(projectDTO, commandFactory, "project"), projectDTO, builder -> {
                                        builder.with(p -> p.setExtra(
                                                        ConversionUtils.convert(projectDTO.getExtra(), commandFactory,
                                                                        "cbor")));
                                });

                return project;
        }

        /**
         * Update a project
         * TODO: x because if element is not passed it override causing empty field
         * 
         * @param project
         * @return
         */
        public Project update(Project project) {
                return EntityFactory.combine(
                                project, projectDTO, builder -> {
                                        builder
                                                        .with(p -> p.setDescription(projectDTO.getDescription()))
                                                        .with(p -> p.setSource(projectDTO.getSource()))
                                                        .with(p -> p.setState(projectDTO.getState() == null
                                                                        ? State.CREATED
                                                                        : State.valueOf(projectDTO.getState())))
                                                        .with(p -> p.setExtra(
                                                                        ConversionUtils.convert(projectDTO.getExtra(),
                                                                                        commandFactory, "cbor")));
                                });
        }
}