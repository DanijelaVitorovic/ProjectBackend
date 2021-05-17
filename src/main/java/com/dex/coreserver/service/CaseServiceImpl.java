package com.dex.coreserver.service;

import com.dex.coreserver.model.*;
import com.dex.coreserver.model.Process;
import com.dex.coreserver.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.dex.coreserver.model.CaseMovement.MovementState.REVOKED;
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

    @Autowired
    private ProcessService processService;

    @Override
    public Case create(Case newCase, String username) {
        User foundUser= userService.findUserByUsername(username);
        Employee foundEmployee= employeeService.findEmployeeByUser(foundUser);
        newCase.setOwner(foundEmployee);
        newCase.setCaseState(Case.CaseState.TAKEOVER);

        ProcessType processType = newCase.getProcess().getProcessType();
        Process process = new Process();
        process.setProcessType(processType);
        Process createdProcess = processService.create(process, username);
        newCase.setProcess(createdProcess);

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
        List<Case> cases = caseRepository.findAll();
        addCaseRemainingDays(cases, username);
        return caseRepository.findAll();
    }

    @Override
    public Case findById(Long id) {
        return caseRepository.findById(id).get();
    }

    private Case setCaseRemainingDays(Case _case, String username) {

        Calendar deadline = Calendar.getInstance();
        deadline.setTime(_case.getStartDate());

        deadline.add(Calendar.DAY_OF_MONTH, _case.getProcess().getProcessType().getDeadline());
        Calendar now = Calendar.getInstance();
        Date currentDate = now.getTime();
        Date deadlineDate = deadline.getTime();

        int diff = (int)((deadlineDate.getTime() - currentDate.getTime()) / (1000*60*60*24)) % 365;
        _case.setRemainingDays(diff);
        return _case;
    }

    private List<Case> addCaseRemainingDays(List<Case> cases, String username) {

        for(Case c: cases) {
            setCaseRemainingDays(c, username);
        }

        return cases;
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

            if(caseMovementRepository.findCaseMovementByCaseAndStateSent(caseForUpdate, SENT) != null) {
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

            if(caseMovementRepository.findCaseMovementByCaseAndStateSent(caseForUpdate, SENT) != null) {
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
    public CaseMovement revokeCaseMovement(Case caseForUpdate, String username) throws Exception {


        if(caseMovementRepository.findCaseMovementByCaseAndStateSent(caseForUpdate, SENT) == null) {
            throw new Exception("Ne postoji predmet za povlačenje zahteva");
        }

        CaseMovement caseMovement= caseMovementRepository.findCaseMovementByCaseAndStateSent(caseForUpdate, SENT);
        caseForUpdate.setCaseState(Case.CaseState.REVOKE);
        caseMovement.setMovementState(CaseMovement.MovementState.REVOKED);
        update(caseForUpdate, username);
        return caseMovementRepository.save(caseMovement);
        }
}
