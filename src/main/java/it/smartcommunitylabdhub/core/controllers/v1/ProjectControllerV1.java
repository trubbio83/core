package it.smartcommunitylabdhub.core.controllers.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.smartcommunitylabdhub.core.annotations.ApiVersion;

@RestController
@RequestMapping("/projects")
@ApiVersion("v1")
public class ProjectControllerV1 {

    @GetMapping("")
    public String getProjects() {
        return "Version 1 of projects controller";
    }

    @GetMapping("/{name}")
    public String getProject(@PathVariable(name = "name", required = true) String name) {
        return null;
    }

    @PostMapping("")
    public String createProject() {
        return null;
    }

}
