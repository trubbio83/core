package it.smartcommunitylabdhub.core.models.dtos;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProjectDTO {
    private String id;
    private String name;
    private String description;
    private String source;
    private Map<String, Object> extra;
    private String state;
    private Date created;
    private Date updated;
    private List<FunctionDTO> functions;
    private List<ArtifactDTO> artifacts;
    private List<WorkflowDTO> workflows;

    @JsonAnyGetter
    public Map<String, Object> getExtra() {
        return extra;
    }

    @JsonAnySetter
    public void setExtra(String key, Object value) {
        extra.put(key, value);
    }

}
