package it.smartcommunitylabdhub.core.services;

import java.util.List;

import org.springframework.data.domain.Pageable;

import it.smartcommunitylabdhub.core.models.converters.CommandFactory;
import it.smartcommunitylabdhub.core.models.dtos.FunctionDTO;
import it.smartcommunitylabdhub.core.models.dtos.RunDTO;
import it.smartcommunitylabdhub.core.repositories.FunctionRepository;
import it.smartcommunitylabdhub.core.services.interfaces.FunctionService;

public class FunctionServiceImpl implements FunctionService {

    private final FunctionRepository functionRepository;
    private final CommandFactory commandFactory;

    public FunctionServiceImpl(
            FunctionRepository functionRepository,
            CommandFactory commandFactory) {
        this.functionRepository = functionRepository;
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
    public List<RunDTO> getFunctionRuns(String uuid) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFunctionRuns'");
    }

}
