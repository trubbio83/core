package it.smartcommunitylabdhub.core.services.interfaces;

import it.smartcommunitylabdhub.core.models.dtos.WorkflowDTO;
import it.smartcommunitylabdhub.core.models.dtos.RunDTO;

import java.util.List;

import org.springframework.data.domain.Pageable;

public interface WorkflowService {
    List<WorkflowDTO> getWorkflows(Pageable pageable);

    WorkflowDTO createWorkflow(WorkflowDTO workflowDTO);

    WorkflowDTO getWorkflow(String uuid);

    WorkflowDTO updateWorkflow(WorkflowDTO workflowDTO, String uuid);

    boolean deleteWorkflow(String uuid);

    List<RunDTO> getWorkflowRuns(String uuid);
}
