package com.dex.coreserver.service;

import com.dex.coreserver.model.ProcessType;
import com.dex.coreserver.repository.ProcessTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessTypeServiceImpl implements ProcessTypeService {

    @Autowired
    private ProcessTypeRepository processTypeRepository;

    @Override
    public ProcessType create(ProcessType processType, String username) {
        return processTypeRepository.save(processType);
    }

    @Override
    public ProcessType update(ProcessType processType, String username) {
        return processTypeRepository.save(processType);
    }

    @Override
    public void delete(Long id, String username) {
        processTypeRepository.deleteById(id);
    }

    @Override
    public List<ProcessType> findAll(String username) {
        return processTypeRepository.findAll();
    }

    @Override
    public ProcessType findById(Long id) {
        return processTypeRepository.findById(id).get();
    }
}
