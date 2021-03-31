package com.dex.coreserver.service;

import com.dex.coreserver.model.LegalEntity;
import com.dex.coreserver.model.Statment;
import com.dex.coreserver.repository.LegalEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LegalEntityServiceImpl implements LegalEntityService {

    @Autowired
    private LegalEntityRepository legalEntityRepository;

    @Override
    public LegalEntity create(LegalEntity entity, String username) {
        return legalEntityRepository.save(entity);
    }

    @Override
    public LegalEntity update(LegalEntity entity, String username) {
        return legalEntityRepository.save(entity);
    }

    @Override
    public void delete(Long id, String username) {
        legalEntityRepository.deleteById(id);
    }

    @Override
    public List<LegalEntity> findAll(String username) {
        return legalEntityRepository.findAll();
    }

    @Override
    public LegalEntity findById(Long id) {
        return legalEntityRepository.findById(id).get();
    }
}
