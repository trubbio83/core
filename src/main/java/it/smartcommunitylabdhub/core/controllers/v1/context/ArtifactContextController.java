package it.smartcommunitylabdhub.core.controllers.v1.context;

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
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import it.smartcommunitylabdhub.core.annotations.ApiVersion;
import it.smartcommunitylabdhub.core.annotations.ValidateField;
import it.smartcommunitylabdhub.core.models.dtos.ArtifactDTO;
import it.smartcommunitylabdhub.core.services.context.interfaces.ArtifactContextService;
import jakarta.validation.Valid;

@RestController
@ApiVersion("v1")
@Validated
public class ArtifactContextController extends ContextController {

        @Autowired
        ArtifactContextService artifactContextService;

        @Operation(summary = "Create an artifact in a project context", description = "First check if project exist and then create the artifact for the project (context)")
        @PostMapping(value = "/artifacts", consumes = { MediaType.APPLICATION_JSON_VALUE,
                        "application/x-yaml" }, produces = "application/json; charset=UTF-8")
        public ResponseEntity<ArtifactDTO> createArtifact(
                        @ValidateField @PathVariable String project,
                        @Valid @RequestBody ArtifactDTO artifactDTO) {
                return ResponseEntity.ok(this.artifactContextService.createArtifact(project, artifactDTO));
        }

        @Operation(summary = "Retrive only the latest version of all artifact", description = "First check if project exist and then return a list of the latest version of each artifact related to a project)")
        @GetMapping(path = "/artifacts", produces = "application/json; charset=UTF-8")
        public ResponseEntity<List<ArtifactDTO>> getLatestArtifacts(
                        @ValidateField @PathVariable String project,
                        Pageable pageable) {

                return ResponseEntity.ok(this.artifactContextService
                                .getLatestByProjectName(project, pageable));
        }

        @Operation(summary = "Retrieve all versions of the artifact sort by creation", description = "First check if project exist and then return a list of all version of the artifact sort by creation)")
        @GetMapping(path = "/artifacts/{name}", produces = "application/json; charset=UTF-8")
        public ResponseEntity<List<ArtifactDTO>> getAllArtifacts(
                        @ValidateField @PathVariable String project,
                        @ValidateField @PathVariable String name,
                        Pageable pageable) {

                return ResponseEntity.ok(this.artifactContextService
                                .getByProjectNameAndArtifactName(project, name, pageable));

        }

        @Operation(summary = "Retrive a specific artifact version given the artifact uuid", description = "First check if project exist and then return a specific version of the artifact identified by the uuid)")
        @GetMapping(path = "/artifacts/{name}/{uuid}", produces = "application/json; charset=UTF-8")
        public ResponseEntity<ArtifactDTO> getArtifactByUuid(
                        @ValidateField @PathVariable String project,
                        @ValidateField @PathVariable String name,
                        @ValidateField @PathVariable String uuid) {

                return ResponseEntity.ok(this.artifactContextService
                                .getByProjectAndArtifactAndUuid(project, name, uuid));

        }

        @Operation(summary = "Retrive the latest version of an artifact", description = "First check if project exist and then return the latest version of an artifact)")
        @GetMapping(path = "/artifacts/{name}/latest", produces = "application/json; charset=UTF-8")
        public ResponseEntity<ArtifactDTO> getLatestArtifactByName(
                        @ValidateField @PathVariable String project,
                        @ValidateField @PathVariable String name) {

                return ResponseEntity.ok(this.artifactContextService
                                .getLatestByProjectNameAndArtifactName(project, name));
        }

        @Operation(summary = "Create an  or update an artifact in a project context", description = "First check if project exist, if artifact exist update one otherwise create a new version of the artifact")
        @PostMapping(value = "/artifacts/{name}", consumes = { MediaType.APPLICATION_JSON_VALUE,
                        "application/x-yaml" }, produces = "application/json; charset=UTF-8")
        public ResponseEntity<ArtifactDTO> createOrUpdateArtifact(
                        @ValidateField @PathVariable String project,
                        @ValidateField @PathVariable String name,
                        @Valid @RequestBody ArtifactDTO artifactDTO) {
                return ResponseEntity
                                .ok(this.artifactContextService.createOrUpdateArtifact(project, name, artifactDTO));
        }

        @Operation(summary = "Update if exist an artifact in a project context", description = "First check if project exist, if artifact exist update.")
        @PutMapping(value = "/artifacts/{name}/{uuid}", consumes = { MediaType.APPLICATION_JSON_VALUE,
                        "application/x-yaml" }, produces = "application/json; charset=UTF-8")
        public ResponseEntity<ArtifactDTO> updateUpdateArtifact(
                        @ValidateField @PathVariable String project,
                        @ValidateField @PathVariable String name,
                        @ValidateField @PathVariable String uuid,
                        @Valid @RequestBody ArtifactDTO artifactDTO) {
                return ResponseEntity.ok(this.artifactContextService.updateArtifact(project, name, uuid, artifactDTO));
        }

        @Operation(summary = "Delete a specific artifact version", description = "First check if project exist, then delete a specific artifact version")
        @DeleteMapping(path = "/artifacts/{name}/{uuid}")
        public ResponseEntity<Boolean> deleteSpecificArtifactVersion(
                        @ValidateField @PathVariable String project,
                        @ValidateField @PathVariable String name,
                        @ValidateField @PathVariable String uuid) {
                return ResponseEntity
                                .ok(this.artifactContextService.deleteSpecificArtifactVersion(project, name, uuid));
        }

        @Operation(summary = "Delete all version of an artifact", description = "First check if project exist, then delete a specific artifact version")
        @DeleteMapping(path = "/artifacts/{name}")
        public ResponseEntity<Boolean> deleteArtifact(
                        @ValidateField @PathVariable String project,
                        @ValidateField @PathVariable String name) {
                return ResponseEntity.ok(this.artifactContextService.deleteAllArtifactVersions(project, name));
        }
}
