package com.dex.coreserver.service;

import com.dex.coreserver.model.Case;
import com.dex.coreserver.model.Employee;
import com.dex.coreserver.model.PhysicalEntity;
import com.dex.coreserver.repository.CaseRepository;
import com.dex.coreserver.repository.EmployeeRepository;
import com.dex.coreserver.repository.PhysicalEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaseServiceImpl implements CaseService {

    @Autowired
    private CaseRepository caseRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PhysicalEntityRepository physicalEntityRepository;

    @Override
    public Case create(Case newCase, String username) {

        Employee employeeOwner = employeeRepository.findById(newCase.getOwner().getId()).get();
        newCase.setOwner(employeeOwner);
        Employee employeeProcessor = employeeRepository.findById(newCase.getProcessor().getId()).get();
        newCase.setProcessor(employeeProcessor);
        PhysicalEntity physicalEntityRefersTo = physicalEntityRepository.findById(newCase.getRefersTo().getId()).get();
        newCase.setRefersTo(physicalEntityRefersTo);
        return caseRepository.save(newCase);
    }

    @Override
    public Case update(Case updatedCase, String username) {
        System.out.println(updatedCase);
        return caseRepository.save(updatedCase);
    }

    @Override
    public void delete(Long id, String username) {
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