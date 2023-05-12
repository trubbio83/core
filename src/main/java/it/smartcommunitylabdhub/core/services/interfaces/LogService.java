package it.smartcommunitylabdhub.core.services.interfaces;

import java.util.List;

import it.smartcommunitylabdhub.core.models.dtos.LogDTO;

public interface LogService {

    List<LogDTO> getLogs();

    LogDTO getLog(String uuid);

    boolean deleteLog(String uuid);

}
