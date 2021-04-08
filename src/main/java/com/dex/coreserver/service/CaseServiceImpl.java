package com.dex.coreserver.service;

import com.dex.coreserver.model.Case;
import com.dex.coreserver.repository.CaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaseServiceImpl implements CaseService{

    @Autowired
    private CaseRepository caseRepository;

    @Override
    public Case create(Case newCase, String username) {
        return caseRepository.save(newCase);
    }

    @Override
    public Case update(Case updatedCase, String username) {
        return caseRepository.save(updatedCase);    }

    @Override
    public void delete(Long id, String username){
        }

    @Override
    public List<Case> findAll(String username) {
        return caseRepository.findAll();
    }

    @Override
    public Case findById(Long id) {
        return caseRepository.findById(id).get();
    }

    @Override
    public List<Case> deleteByIdAndReturnFindAll(Long id, String username) {
        caseRepository.deleteById(id);
        return caseRepository.findAll();
    }
}
