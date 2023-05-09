package it.smartcommunitylabdhub.core.models.dtos;

import java.util.Date;
import java.util.Map;

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
public class FunctionDTO {
    private String id;
    private String name;
    private String kind;
    private String project;
    private Map<String, Object> spec;
    private Map<String, Object> extra;

    private Date created;
    private Date updated;
    private Boolean embedded;
    private String state;
}
