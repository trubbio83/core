package it.smartcommunitylabdhub.core.models.converters.models;

import org.springframework.stereotype.Component;

import it.smartcommunitylabdhub.core.exception.CustomException;
import it.smartcommunitylabdhub.core.models.Workflow;
import it.smartcommunitylabdhub.core.models.converters.interfaces.Converter;
import it.smartcommunitylabdhub.core.models.dtos.WorkflowDTO;

@Component
public class WorkflowConverter implements Converter<Workflow, WorkflowDTO> {

    @Override
    public WorkflowDTO convert(Workflow workflow) throws CustomException {
        return new WorkflowDTO(workflow.getId(), workflow.getName());
    }

    @Override
    public Workflow convertReverse(WorkflowDTO workflowDTO) throws CustomException {
        return Workflow.builder().name(workflowDTO.getName()).build();
    }

}