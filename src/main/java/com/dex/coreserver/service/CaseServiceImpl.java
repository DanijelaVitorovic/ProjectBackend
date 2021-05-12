package com.dex.coreserver.service;

import com.dex.coreserver.model.*;
import com.dex.coreserver.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import static com.dex.coreserver.model.CaseMovement.MovementState.SENT;

@Service
public class CaseServiceImpl implements CaseService {

    @Autowired
    private CaseRepository caseRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CaseMovementRepository caseMovementRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PhysicalEntityRepository physicalEntityRepository;

    @Override
    public Case create(Case newCase, String username) {

        User foundUser= userService.findUserByUsername(username);
        Employee foundEmployee= employeeService.findEmployeeByUser(foundUser);
        newCase.setOwner(foundEmployee);
        newCase.setCaseState(Case.CaseState.TAKEOVER);

        return caseRepository.save(newCase);
    }

    @Override
    public Case update(Case updatedCase, String username) {
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

    @Transactional
    @Override
    public CaseMovement addOwner(CaseMovement caseMovement, String username) throws Exception {

        Case caseForUpdate= findById(caseMovement.get_case().getId());

            if(caseMovementRepository.findCaseMovementByCaseAndStateSent(caseForUpdate, SENT).size() != 0) {
                throw new Exception("Nazalost vas predmet je u nekom drugom procesu dodele");
            }

        User foundUser= userService.findUserByUsername(username);
        Employee foundEmployee= employeeService.findEmployeeByUser(foundUser);

        caseForUpdate.setCaseState(Case.CaseState.ASSIGN);

        caseMovement.setEmployeeSend(foundEmployee);
        caseMovement.setSendTime(new Date());
        caseMovement.set_case(caseForUpdate);
        caseMovement.setMovementState(CaseMovement.MovementState.SENT);
        update(caseForUpdate, username);

         return caseMovementRepository.save(caseMovement);
    }

    @Transactional
    @Override
    public CaseMovement addProcessor(CaseMovement caseMovement, String username) throws Exception {


            Case caseForUpdate= findById(caseMovement.get_case().getId());

            if(caseMovementRepository.findCaseMovementByCaseAndStateSent(caseForUpdate, SENT).size() != 0) {
            throw new Exception("Nazalost vas predmet je u nekom drugom procesu dodele");
            }

            User foundUser= userService.findUserByUsername(username);
            Employee foundEmployee= employeeService.findEmployeeByUser(foundUser);

            caseForUpdate.setCaseState(Case.CaseState.ASSIGN);

            caseMovement.setEmployeeSend(foundEmployee);
            caseMovement.setSendTime(new Date());
            caseMovement.set_case(caseForUpdate);
            caseMovement.setMovementState(CaseMovement.MovementState.SENT);
            update(caseForUpdate, username);

            return caseMovementRepository.save(caseMovement);
    }
}
