package it.smartcommunitylabdhub.core.controllers.v2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.smartcommunitylabdhub.core.annotations.ApiVersion;

@RestController
@RequestMapping("/projects")
@ApiVersion("v2")
public class ProjectControllerV2 {

    @GetMapping("/")
    public String getProjects() {
        return "Version 2 of projects controller";
    }

}
