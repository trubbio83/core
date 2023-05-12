package it.smartcommunitylabdhub.core.services.interfaces;

import it.smartcommunitylabdhub.core.models.Run;
import it.smartcommunitylabdhub.core.models.dtos.FunctionDTO;
import java.util.List;

import org.springframework.data.domain.Pageable;

public interface FunctionService {
    List<FunctionDTO> getFunctions(Pageable pageable);

    FunctionDTO createFunction(FunctionDTO projectDTO);

    FunctionDTO getFunction(String uuid);

    FunctionDTO updateFunction(FunctionDTO projectDTO, String uuid);

    boolean deleteFunction(String uuid);

    List<Run> getFunctionRuns(String uuid);
}
