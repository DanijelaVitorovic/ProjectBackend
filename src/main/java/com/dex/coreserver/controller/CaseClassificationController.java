package com.dex.coreserver.controller;

import com.dex.coreserver.model.AbstractDataModel;
import com.dex.coreserver.model.CaseClassification;
import com.dex.coreserver.service.CaseClassificationService;
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
@RequestMapping("/api/caseClassification")
public class CaseClassificationController{

    @Autowired
    private CaseClassificationService caseClassificationService;

    @Autowired
    MapValidationErrorService mapValidationErrorService;

    @PostMapping("/createCaseClassification")
    public ResponseEntity<?> create(@Valid @RequestBody CaseClassification caseClassificationForCreate, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;
        CaseClassification newCaseClassification = caseClassificationService.create(caseClassificationForCreate, principal.getName());
        return new ResponseEntity<CaseClassification>(newCaseClassification, HttpStatus.CREATED);
    }

    @PostMapping("/updateCaseClassification")
    public ResponseEntity<?> update(@Valid @RequestBody CaseClassification caseClassificationForUpdate, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;
        CaseClassification updatedCaseClassification =  caseClassificationService.update(caseClassificationForUpdate, principal.getName());
        return new ResponseEntity<CaseClassification>(updatedCaseClassification, HttpStatus.CREATED);
    }

    @GetMapping("/findAllCaseClassifications")
    public List<CaseClassification> findAll(Principal principal) {
        return caseClassificationService.findAll(principal.getName());
    }

    @GetMapping("/findCaseClassification/{id}")
    private ResponseEntity<?> findCaseClassificationById(@PathVariable Long id, Principal principal) {
        CaseClassification foundCaseClassification = caseClassificationService.findById(id);
        return new ResponseEntity<CaseClassification>(foundCaseClassification, HttpStatus.OK);
    }

    @DeleteMapping("/deleteCaseClassification/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Principal principal) {
        caseClassificationService.delete(id, principal.getName());
        return new ResponseEntity<String>("Case Classification deleted", HttpStatus.OK);
    }
}
