package com.dex.coreserver.service;

import com.dex.coreserver.model.Case;
import com.dex.coreserver.model.CaseMovement;
import com.dex.coreserver.model.Employee;
import com.dex.coreserver.repository.CaseMovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.dex.coreserver.model.CaseMovement.MovementState.RECEIVED;

@Service
public class CaseMovementServiceImpl implements CaseMovementService{

    @Autowired
    UserService userService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    CaseService caseService;

    @Autowired
    CaseMovementRepository caseMovementRepository;

    @Override
    public List<CaseMovement> getCaseMovementList(String username){
        CaseMovement.MovementState sent =CaseMovement.MovementState.SENT;
        Employee foundEmployee= employeeService.findEmployeeByUser(userService.findUserByUsername(username));
        List<CaseMovement> listOfCaseMovements= caseMovementRepository.findCaseMovementByOwnerOrProcessorIdAndStateSent(foundEmployee, sent);

        return listOfCaseMovements;
    }

    @Transactional
    @Override
    public CaseMovement acceptCase(Long id, String username){
        CaseMovement caseMovement=caseMovementRepository.findById(id).get();

        caseMovement.setMovementState(RECEIVED);
        Case acceptedCase= caseMovement.get_case();

        if(caseMovement.getEmployeeOwner()!=null){
            acceptedCase.setOwner(caseMovement.getEmployeeOwner()); }

        if(caseMovement.getEmployeeProcessor()!=null){
            acceptedCase.setProcessor(caseMovement.getEmployeeProcessor()); }

        caseService.create(acceptedCase, username);

        return caseMovementRepository.save(caseMovement);
    }

    @Override
    public List<CaseMovement> findByCaseId(Long id) {
        return caseMovementRepository.findBy_case(caseService.findById(id));
    }
}
