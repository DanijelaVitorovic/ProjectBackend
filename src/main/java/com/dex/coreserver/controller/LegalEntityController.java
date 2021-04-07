package com.dex.coreserver.controller;

import com.dex.coreserver.model.LegalEntity;
import com.dex.coreserver.service.LegalEntityService;
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
@RequestMapping("/api/legalEntity")
public class LegalEntityController {

    @Autowired
    private LegalEntityService legalEntityService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/create")
    public ResponseEntity<?> createNewEntity(@Valid @RequestBody LegalEntity legalEntity, BindingResult result, Principal principal){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);

        if(errorMap  != null)
            return errorMap;

        LegalEntity createdLegalEntity = legalEntityService.create(legalEntity, principal.getName());
        return new ResponseEntity<LegalEntity>(createdLegalEntity, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateNewEntity(@Valid @RequestBody LegalEntity legalEntity, BindingResult result, Principal principal){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);

        if(errorMap  != null)
            return errorMap;

        LegalEntity updatedLegalEntity = legalEntityService.update(legalEntity, principal.getName());
        return new ResponseEntity<LegalEntity>(updatedLegalEntity, HttpStatus.CREATED);
    }

    @GetMapping("/findAll")
    public List<LegalEntity> findAll(Principal principal) {
        List<LegalEntity> legalEntities = legalEntityService.findAll(principal.getName());
        return legalEntities;
    }

    @GetMapping("/find/{id}")
    private ResponseEntity<?> findLegalEntityById(@PathVariable Long id) {
        LegalEntity legalEntity = legalEntityService.findById(id);
        return new ResponseEntity<LegalEntity>(legalEntity, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id, Principal principal){
        legalEntityService.delete(id,principal.getName());
    }

}
