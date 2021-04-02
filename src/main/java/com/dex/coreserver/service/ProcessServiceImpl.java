package com.dex.coreserver.service;

import com.dex.coreserver.model.Process;
import com.dex.coreserver.model.ProcessType;
import com.dex.coreserver.repository.ProcessRepository;
import com.dex.coreserver.repository.ProcessTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
public class ProcessServiceImpl implements ProcessService{

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private ProcessTypeRepository processTypeRepository;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Override
    public Process create(Process process, String username) {
        return processRepository.save(process);
    }

    @Override
    public Process update(Process process, String username) {
        return processRepository.save(process);
    }

    @Override
    public void delete(Long id, String username) {
        processRepository.deleteById(id);
    }

    @Override
    public List<Process> findAll(String username) {
        return processRepository.findAll();
    }

    @Override
    public Process findById(Long id) {
        return processRepository.findById(id).get();
    }
}
