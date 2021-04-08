package com.dex.coreserver.controller;

import com.dex.coreserver.model.Case;
import com.dex.coreserver.service.CaseService;
import com.dex.coreserver.service.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/case")
public class CaseController {

    @Autowired CaseService caseService;

    @Autowired MapValidationErrorService mapValidationErrorService;

    @PostMapping("/createCase")
    public ResponseEntity<?> create(@Valid @RequestBody Case caseForCreate, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;
        Case newCase = caseService.create(caseForCreate, principal.getName());
        return new ResponseEntity<Case>(newCase, HttpStatus.CREATED);
    }

    @PostMapping("/updateCase")
    public ResponseEntity<?> update(@Valid @RequestBody Case caseForUpdate, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;
        Case updatedCase =  caseService.update(caseForUpdate, principal.getName());
        return new ResponseEntity<Case>(updatedCase, HttpStatus.CREATED);
    }

    @GetMapping("/findAllCases")
    public List<Case> findAll(Principal principal) {
        return caseService.findAll(principal.getName());
    }

    @GetMapping("/findCase/{id}")
    private ResponseEntity<?> findCaseById(@PathVariable Long id, Principal principal) {
        Case foundCase = caseService.findById(id);
        return new ResponseEntity<Case>(foundCase, HttpStatus.OK);
    }

    @DeleteMapping("/deleteCase/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Case> deleteCase(@PathVariable Long id, Principal principal){
        return caseService.deleteByIdAndReturnFindAll(id,principal.getName());
    }
}
