package it.smartcommunitylabdhub.core.controllers.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import it.smartcommunitylabdhub.core.annotations.ApiVersion;
import it.smartcommunitylabdhub.core.annotations.ValidateField;
import it.smartcommunitylabdhub.core.models.dtos.ArtifactDTO;
import it.smartcommunitylabdhub.core.models.dtos.FunctionDTO;
import it.smartcommunitylabdhub.core.models.dtos.ProjectDTO;
import it.smartcommunitylabdhub.core.models.dtos.WorkflowDTO;
import it.smartcommunitylabdhub.core.services.interfaces.ProjectService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/projects")
@ApiVersion("v1")
@Validated
public class ProjectControllerV1 {

    @Autowired
    ProjectService projectService;

    @Operation(summary = "List project", description = "Return a list of all projects")
    @GetMapping(path = "", produces = "application/json; charset=UTF-8")
    public ResponseEntity<List<ProjectDTO>> getProjects(Pageable pageable) {
        return ResponseEntity.ok(this.projectService.getProjects(pageable));
    }

    @Operation(summary = "Create project", description = "Create an project and return")
    @PostMapping(value = "", consumes = { MediaType.APPLICATION_JSON_VALUE,
            "application/x-yaml" }, produces = "application/json; charset=UTF-8")
    public ResponseEntity<ProjectDTO> createProject(@Valid @RequestBody ProjectDTO projectDTO) {
        return ResponseEntity.ok(this.projectService.createProject(projectDTO));
    }

    @Operation(summary = "Get an project by uuid", description = "Return an project")
    @GetMapping(path = "/{uuidOrName}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<ProjectDTO> getProject(
            @ValidateField @PathVariable(name = "uuidOrName", required = true) String uuidOrName) {
        return ResponseEntity.ok(this.projectService.getProject(uuidOrName));
    }

    @Operation(summary = "Update specific project", description = "Update and return the project")
    @PutMapping(path = "/{uuidOrName}", consumes = { MediaType.APPLICATION_JSON_VALUE,
            "application/x-yaml" }, produces = "application/json; charset=UTF-8")
    public ResponseEntity<ProjectDTO> updateProject(
            @RequestBody ProjectDTO projectDTO,
            @ValidateField @PathVariable(name = "uuidOrName", required = true) String uuidOrName) {
        return ResponseEntity.ok(this.projectService.updateProject(projectDTO, uuidOrName));
    }

    @Operation(summary = "Delete a project", description = "Delete a specific project")
    @DeleteMapping(path = "/{uuidOrName}")
    public ResponseEntity<Boolean> deleteProject(
            @ValidateField @PathVariable(name = "uuidOrName", required = true) String uuidOrName) {
        return ResponseEntity.ok(this.projectService.deleteProject(uuidOrName));
    }

    @Operation(summary = "List project functions", description = "Get all project function list")
    @GetMapping(path = "/{uuidOrName}/functions", produces = "application/json; charset=UTF-8")
    public ResponseEntity<List<FunctionDTO>> projectFunctions(@ValidateField @PathVariable String uuidOrName) {
        return ResponseEntity.ok(this.projectService.getProjectFunctions(uuidOrName));
    }

    @Operation(summary = "List project workflows", description = "Get all project workflow list")
    @GetMapping(path = "/{uuidOrName}/workflows", produces = "application/json; charset=UTF-8")
    public ResponseEntity<List<WorkflowDTO>> projectWorkflows(@ValidateField @PathVariable String uuidOrName) {
        return ResponseEntity.ok(this.projectService.getProjectWorkflows(uuidOrName));
    }

    @Operation(summary = "List project artifacts", description = "Get all project artifact list")
    @GetMapping(path = "/{uuidOrName}/artifacts", produces = "application/json; charset=UTF-8")
    public ResponseEntity<List<ArtifactDTO>> projectArtifacts(@ValidateField @PathVariable String uuidOrName) {
        return ResponseEntity.ok(this.projectService.getProjectArtifacts(uuidOrName));
    }

}
