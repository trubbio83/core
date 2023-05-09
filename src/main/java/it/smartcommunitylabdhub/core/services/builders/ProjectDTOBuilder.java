package it.smartcommunitylabdhub.core.services.builders;

import java.util.List;
import java.util.Map;

import it.smartcommunitylabdhub.core.models.dtos.ArtifactDTO;
import it.smartcommunitylabdhub.core.models.dtos.FunctionDTO;
import it.smartcommunitylabdhub.core.models.dtos.ProjectDTO;
import it.smartcommunitylabdhub.core.models.dtos.WorkflowDTO;

public class ProjectDTOBuilder {
    private String id;
    private String name;
    private String description;
    private String source;
    private Map<String, Object> extra;
    private String state;

    private List<FunctionDTO> functions;
    private List<ArtifactDTO> artifacts;
    private List<WorkflowDTO> workflows;

    public ProjectDTOBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public ProjectDTOBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ProjectDTOBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public ProjectDTOBuilder setSource(String source) {
        this.source = source;
        return this;
    }

    public ProjectDTOBuilder setExtra(Map<String, Object> extra) {
        this.extra = extra;
        return this;
    }

    public ProjectDTOBuilder setState(String state) {
        this.state = state;
        return this;
    }

    public ProjectDTOBuilder setFunctions(List<FunctionDTO> functions) {
        this.functions = functions;
        return this;
    }

    public ProjectDTOBuilder setArtifacts(List<ArtifactDTO> artifacts) {
        this.artifacts = artifacts;
        return this;
    }

    public ProjectDTOBuilder setWorkflows(List<WorkflowDTO> workflows) {
        this.workflows = workflows;
        return this;
    }

    public ProjectDTO build() {
        return new ProjectDTO(
                id, name, description, source,
                extra, state,
                functions, artifacts, workflows);
    }
}
