package it.smartcommunitylabdhub.core.models.converters.models;

import org.springframework.stereotype.Component;

import it.smartcommunitylabdhub.core.exception.CustomException;
import it.smartcommunitylabdhub.core.models.Artifact;
import it.smartcommunitylabdhub.core.models.converters.interfaces.Converter;
import it.smartcommunitylabdhub.core.models.dtos.ArtifactDTO;

@Component
public class ArtifactConverter implements Converter<Artifact, ArtifactDTO> {

    @Override
    public ArtifactDTO convert(Artifact artifact) throws CustomException {
        return new ArtifactDTO(artifact.getId(), artifact.getName());
    }

    @Override
    public Artifact convertReverse(ArtifactDTO artifactDTO) throws CustomException {
        return Artifact.builder().name(artifactDTO.getName()).build();
    }

}
