package it.smartcommunitylabdhub.core.services.builders.daos;

import it.smartcommunitylabdhub.core.models.Project;
import it.smartcommunitylabdhub.core.models.converters.CommandFactory;
import it.smartcommunitylabdhub.core.models.converters.ConversionUtils;
import it.smartcommunitylabdhub.core.models.dtos.ProjectDTO;
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

        public Project build() {
                Project project = ConversionUtils.convert(projectDTO, commandFactory, "project");
                EntityFactory.combine(project, projectDTO, builder -> {
                        builder.with(p -> p.setExtra(
                                        ConversionUtils.convert(projectDTO.getExtra(), commandFactory, "cbor")));
                });

                return project;
        }
}