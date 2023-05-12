package it.smartcommunitylabdhub.core.services;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import it.smartcommunitylabdhub.core.exceptions.CoreException;
import it.smartcommunitylabdhub.core.exceptions.CustomException;
import it.smartcommunitylabdhub.core.models.converters.CommandFactory;
import it.smartcommunitylabdhub.core.models.converters.ConversionUtils;
import it.smartcommunitylabdhub.core.models.dtos.RunDTO;
import it.smartcommunitylabdhub.core.models.entities.Run;
import it.smartcommunitylabdhub.core.repositories.RunRepository;
import it.smartcommunitylabdhub.core.services.interfaces.RunService;

@Service
public class RunSerivceImpl implements RunService {

    private final RunRepository runRepository;
    private final CommandFactory commandFactory;

    public RunSerivceImpl(RunRepository runRepository, CommandFactory commandFactory) {
        this.runRepository = runRepository;
        this.commandFactory = commandFactory;
    }

    @Override
    public List<RunDTO> getRuns(Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRuns'");
    }

    @Override
    public RunDTO getRun(String uuid) {
        final Run run = runRepository.findById(uuid).orElse(null);
        if (run == null) {
            throw new CoreException(
                    "RunNotFound",
                    "The run you are searching for does not exist.",
                    HttpStatus.NOT_FOUND);
        }

        try {
            return ConversionUtils.reverse(run, commandFactory, "run");

        } catch (CustomException e) {
            throw new CoreException(
                    "InternalServerError",
                    e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean deleteRun(String uuid) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteRun'");
    }

}
