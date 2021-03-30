package com.dex.coreserver.service;

import com.dex.coreserver.model.PhysicalEntity;
import com.dex.coreserver.repository.PhysicalEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PhysicalEntityServiceImpl implements PhysicalEntityService {

    @Autowired
    PhysicalEntityRepository physicalEntityRepository;

    @Override
    public PhysicalEntity create(PhysicalEntity entity, String username) {
        return physicalEntityRepository.save(entity);
    }

    @Override
    public PhysicalEntity update(PhysicalEntity entity, String username) {
        return physicalEntityRepository.save(entity);
    }

    @Override
    public void delete(Long id, String username) {
        physicalEntityRepository.deleteById(id);
    }

    @Override
    public List<PhysicalEntity> findAll(String username) {
        return physicalEntityRepository.findAll();
    }

    @Override
    public PhysicalEntity findById(Long id) {
        return physicalEntityRepository.findById(id).get();
    }

}
