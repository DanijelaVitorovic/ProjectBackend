package com.dex.coreserver.service;

import com.dex.coreserver.model.CaseClassification;
import com.dex.coreserver.repository.CaseClassificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CaseClassificationServiceImpl implements CaseClassificationService{

    @Autowired
    private CaseClassificationRepository caseClassificationRepository;

    @Override
    public CaseClassification create(CaseClassification newCaseClassification, String username) {
        return caseClassificationRepository.save(newCaseClassification);
    }

    @Override
    public CaseClassification update(CaseClassification updatedCaseClassification, String username) {
        return caseClassificationRepository.save(updatedCaseClassification);
    }

    @Override
    public void delete(Long id, String username) {
        caseClassificationRepository.deleteById(id);
    }

    @Override
    public List<CaseClassification> findAll(String username) {
        return caseClassificationRepository.findAll();
    }

    @Override
    public CaseClassification findById(Long id) {
        return caseClassificationRepository.findById(id).get();
    }
}
