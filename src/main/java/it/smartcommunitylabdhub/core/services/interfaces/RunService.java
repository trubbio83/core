package it.smartcommunitylabdhub.core.services.interfaces;

import java.util.List;

import org.springframework.data.domain.Pageable;

import it.smartcommunitylabdhub.core.models.dtos.RunDTO;
import it.smartcommunitylabdhub.core.models.dtos.custom.RunExecDTO;

public interface RunService {

    List<RunDTO> getRuns(Pageable pageable);

    RunDTO getRun(String uuid);

    boolean deleteRun(String uuid);

    RunDTO save(RunDTO runDTO);

    RunDTO createRun(RunExecDTO runExecDTO);

}
