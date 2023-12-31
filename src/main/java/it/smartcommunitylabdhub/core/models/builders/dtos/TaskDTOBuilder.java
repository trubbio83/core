package it.smartcommunitylabdhub.core.models.builders.dtos;

import org.springframework.stereotype.Component;

import it.smartcommunitylabdhub.core.models.builders.EntityFactory;
import it.smartcommunitylabdhub.core.models.converters.ConversionUtils;
import it.smartcommunitylabdhub.core.models.dtos.TaskDTO;
import it.smartcommunitylabdhub.core.models.entities.Task;
import it.smartcommunitylabdhub.core.models.enums.State;

@Component
public class TaskDTOBuilder {

        public TaskDTO build(Task task) {
                return EntityFactory.create(TaskDTO::new, task, builder -> {
                        builder
                                        .with(dto -> dto.setId(task.getId()))
                                        .with(dto -> dto.setTask(task.getTask()))
                                        .with(dto -> dto.setProject(task.getProject()))
                                        .with(dto -> dto.setKind(task.getKind()))
                                        .with(dto -> dto.setSpec(ConversionUtils.reverse(task.getSpec(), "cbor")))
                                        .with(dto -> dto.setExtra(ConversionUtils.reverse(task.getExtra(), "cbor")))
                                        .with(dto -> dto.setCreated(task.getCreated()))
                                        .with(dto -> dto.setUpdated(task.getUpdated()))
                                        .with(dto -> dto.setState(task.getState() == null
                                                        ? State.CREATED.name()
                                                        : task.getState()
                                                                        .name()));

                });
        }
}