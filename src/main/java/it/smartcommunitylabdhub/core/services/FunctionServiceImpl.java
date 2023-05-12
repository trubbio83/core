package it.smartcommunitylabdhub.core.services;

import java.util.List;

import org.springframework.data.domain.Pageable;

import it.smartcommunitylabdhub.core.models.Run;
import it.smartcommunitylabdhub.core.models.converters.CommandFactory;
import it.smartcommunitylabdhub.core.models.dtos.FunctionDTO;
import it.smartcommunitylabdhub.core.repositories.FunctionRepository;
import it.smartcommunitylabdhub.core.repositories.RunRepository;
import it.smartcommunitylabdhub.core.services.interfaces.FunctionService;

public class FunctionServiceImpl implements FunctionService {

    private final FunctionRepository functionRepository;
    private final RunRepository runRepository;
    private final CommandFactory commandFactory;

    public FunctionServiceImpl(
            FunctionRepository functionRepository,
            RunRepository runRepository,
            CommandFactory commandFactory) {
        this.functionRepository = functionRepository;
        this.runRepository = runRepository;
        this.commandFactory = commandFactory;

    }

    @Override
    public List<FunctionDTO> getFunctions(Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFunctions'");
    }

    @Override
    public FunctionDTO createFunction(FunctionDTO projectDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createFunction'");
    }

    @Override
    public FunctionDTO getFunction(String uuid) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFunction'");
    }

    @Override
    public FunctionDTO updateFunction(FunctionDTO projectDTO, String uuid) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateFunction'");
    }

    @Override
    public boolean deleteFunction(String uuid) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteFunction'");
    }

    @Override
    public List<Run> getFunctionRuns(String uuid) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFunctionRuns'");
    }

}
