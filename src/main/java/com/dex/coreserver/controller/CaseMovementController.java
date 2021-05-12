package com.dex.coreserver.controller;

import com.dex.coreserver.model.CaseMovement;
import com.dex.coreserver.repository.CaseMovementRepository;
import com.dex.coreserver.service.CaseMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/caseMovement")
public class CaseMovementController {

    @Autowired
    CaseMovementService caseMovementService;

    @Autowired
    CaseMovementRepository caseMovementRepository;

    @GetMapping("/findAllCaseMovements")
    public List<CaseMovement> findAllCaseMovements(Principal principal) {
        return caseMovementService.getCaseMovementList(principal.getName());
    }

    @PatchMapping("/acceptCase/{id}")
    public ResponseEntity<?> acceptCase(@PathVariable Long id, Principal principal) {
        return new ResponseEntity<CaseMovement>(caseMovementService.acceptCase(id, principal.getName()),HttpStatus.OK);
    }

    @GetMapping("/findCaseMovementByCaseId/{id}")
    public List<CaseMovement> findCaseMovementByCaseId(@PathVariable Long id) {
        return caseMovementService.findByCaseId(id);
    }
}
