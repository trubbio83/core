package it.smartcommunitylabdhub.core.services.interfaces;

import java.util.List;

import it.smartcommunitylabdhub.core.models.dtos.RunDTO;

public interface RunService {

    List<RunDTO> getRuns();

    RunDTO getRun(String uuid);

    boolean deleteRun(String uuid);

}
