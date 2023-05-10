package it.smartcommunitylabdhub.core.controllers.v1;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.smartcommunitylabdhub.core.annotations.ApiVersion;
import it.smartcommunitylabdhub.core.models.dtos.ProjectDTO;
import it.smartcommunitylabdhub.core.services.interfaces.ProjectService;

@RestController
@RequestMapping("/projects")
@ApiVersion("v1")
public class ProjectControllerV1 {

    private final ProjectService projectService;

    public ProjectControllerV1(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping(path = "", produces = "application/json; charset=UTF-8")
    public ResponseEntity<List<ProjectDTO>> getProjects() {
        return ResponseEntity.ok(this.projectService.getProjects());
    }

    @PostMapping(path = "", produces = "application/json; charset=UTF-8")
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO) {
        return ResponseEntity.ok(this.projectService.createProject(projectDTO));
    }

    @GetMapping(path = "/{uuid}", produces = "application/json; charset=UTF-8")
    public String getProject(@PathVariable(name = "uuid", required = true) String name) {
        return null;
    }

    @GetMapping(produces = "application/json", consumes = "application/json", path = "/json")
    public ResponseEntity<String> getJson() {
        return ResponseEntity.ok("hello I'm a json");

    }

    @GetMapping(produces = "text/plain", consumes = "text/plain", path = "/json")
    @ResponseBody
    public String getJson1() {
        return "hello I'm a string";
    }

}
