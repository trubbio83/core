package it.smartcommunitylabdhub.mlrun.components.kinds;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import it.smartcommunitylabdhub.core.annotations.RunBuilderComponent;
import it.smartcommunitylabdhub.core.components.kinds.factory.builders.KindBuilder;
import it.smartcommunitylabdhub.core.exceptions.CoreException;
import it.smartcommunitylabdhub.core.models.accessors.utils.TaskAccessor;
import it.smartcommunitylabdhub.core.models.accessors.utils.TaskUtils;
import it.smartcommunitylabdhub.core.models.dtos.FunctionDTO;
import it.smartcommunitylabdhub.core.models.dtos.RunDTO;
import it.smartcommunitylabdhub.core.models.dtos.TaskDTO;
import it.smartcommunitylabdhub.core.repositories.TaskRepository;
import it.smartcommunitylabdhub.core.services.interfaces.FunctionService;

@RunBuilderComponent(type = "job")
public class JobRunBuilder implements KindBuilder<TaskDTO, RunDTO> {
        @Autowired
        TaskRepository taskRepository;

        @Autowired
        FunctionService functionService;

        @Override
        public RunDTO build(TaskDTO taskDTO) {
                // 1. get function get if exist otherwise throw exeception.
                return taskRepository.findById(taskDTO.getId())
                                .map(task -> {
                                        // 1. produce function object for mlrun and put it on spec.
                                        TaskAccessor taskAccessor = TaskUtils.parseTask(taskDTO.getTask());

                                        FunctionDTO functionDTO = functionService
                                                        .getFunction(taskAccessor.getVersion());

                                        // 2. set function on spec for mlrun
                                        return Optional.ofNullable(functionDTO.getExtra().get("mlrun_hash"))
                                                        .map(mlrunHash -> {

                                                                // 3. set function on spec
                                                                taskDTO.getSpec().put("function",
                                                                                functionDTO.getProject()
                                                                                                + "/"
                                                                                                + functionDTO.getName()
                                                                                                + "@"
                                                                                                + mlrunHash);

                                                                // 4. Merge Task spec with function spec
                                                                functionDTO.getSpec().putAll(taskDTO.getSpec());

                                                                // 5. produce a run object and store it
                                                                return RunDTO.builder()
                                                                                .kind("run")
                                                                                .taskId(task.getId())
                                                                                .project(task.getProject())
                                                                                .task(task.getTask())
                                                                                .spec(functionDTO.getSpec())
                                                                                .build();

                                                        }).orElseThrow(() -> new CoreException("MLrunHashNotFound",
                                                                        "Cannot prepare mlrun function. Mlrun hash not found!",
                                                                        HttpStatus.INTERNAL_SERVER_ERROR));

                                }).orElseThrow(() -> new CoreException(
                                                "FunctionNotFound",
                                                "The function you are searching for does not exist.",
                                                HttpStatus.NOT_FOUND));

        }
}