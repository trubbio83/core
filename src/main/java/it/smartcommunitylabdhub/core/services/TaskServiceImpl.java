package it.smartcommunitylabdhub.core.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import it.smartcommunitylabdhub.core.exceptions.CoreException;
import it.smartcommunitylabdhub.core.exceptions.CustomException;
import it.smartcommunitylabdhub.core.models.builders.dtos.TaskDTOBuilder;
import it.smartcommunitylabdhub.core.models.builders.entities.TaskEntityBuilder;
import it.smartcommunitylabdhub.core.models.dtos.TaskDTO;
import it.smartcommunitylabdhub.core.models.entities.Task;
import it.smartcommunitylabdhub.core.repositories.TaskRepository;
import it.smartcommunitylabdhub.core.services.interfaces.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskDTOBuilder taskDTOBuilder;

    @Autowired
    TaskEntityBuilder taskEntityBuilder;

    @Override
    public List<TaskDTO> getTasks(Pageable pageable) {
        try {
            Page<Task> TaskPage = this.taskRepository.findAll(pageable);
            return TaskPage.getContent().stream()
                    .map(task -> taskDTOBuilder.build(task))
                    .collect(Collectors.toList());

        } catch (CustomException e) {
            throw new CoreException(
                    "InternalServerError",
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public TaskDTO getTask(String uuid) {
        return taskRepository.findById(uuid).map(task -> taskDTOBuilder.build(task))
                .orElseThrow(() -> new CoreException(
                        "TaskNotFound",
                        "The Task you are searching for does not exist.",
                        HttpStatus.NOT_FOUND));
    }

    @Override
    public boolean deleteTask(String uuid) {
        try {
            this.taskRepository.deleteById(uuid);
            return true;
        } catch (Exception e) {
            throw new CoreException(
                    "InternalServerError",
                    "cannot delete artifact",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        if (taskDTO.getId() != null && taskRepository.existsById(taskDTO.getId())) {
            throw new CoreException("DuplicateTaskId",
                    "Cannot create the task", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<Task> savedTask = Optional.ofNullable(taskDTO)
                .map(taskEntityBuilder::build)
                .map(this.taskRepository::save);

        return savedTask.map(task -> taskDTOBuilder.build(task))
                .orElseThrow(() -> new CoreException(
                        "InternalServerError",
                        "Error saving task",
                        HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Override
    public TaskDTO updateTask(TaskDTO taskDTO, String uuid) {
        if (!taskDTO.getId().equals(uuid)) {
            throw new CoreException(
                    "TaskNotMatch",
                    "Trying to update a task with an uuid different from the one passed in the request.",
                    HttpStatus.NOT_FOUND);
        }

        final Task task = taskRepository.findById(uuid).orElse(null);
        if (task == null) {
            throw new CoreException(
                    "TaskNotFound",
                    "The task you are searching for does not exist.",
                    HttpStatus.NOT_FOUND);
        }

        try {
            final Task taskUpdated = taskEntityBuilder.update(task, taskDTO);
            this.taskRepository.save(taskUpdated);

            return taskDTOBuilder.build(taskUpdated);

        } catch (CustomException e) {
            throw new CoreException(
                    "InternalServerError",
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
