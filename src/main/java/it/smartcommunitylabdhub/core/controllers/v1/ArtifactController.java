package it.smartcommunitylabdhub.core.controllers.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.smartcommunitylabdhub.core.annotations.ApiVersion;

@RestController
@RequestMapping("/artifacts")
@ApiVersion("v1")
public class ArtifactController {

    public String artifacts() {
        return "Artifact version 1";
    }

}
