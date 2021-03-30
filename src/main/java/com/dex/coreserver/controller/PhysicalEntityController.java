package com.dex.coreserver.controller;

import com.dex.coreserver.model.PhysicalEntity;
import com.dex.coreserver.service.MapValidationErrorService;
import com.dex.coreserver.service.PhysicalEntityService;
import com.dex.coreserver.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/physicalEntity")
public class PhysicalEntityController {

    @Autowired
    PhysicalEntityService physicalEntityService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    UserValidator userValidator;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody PhysicalEntity physicalEntity, BindingResult result, Principal principal) {
        userValidator.validate(physicalEntity, result);
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;
        physicalEntityService.create(physicalEntity, principal.getName());
        return new ResponseEntity<PhysicalEntity>(physicalEntity, HttpStatus.CREATED);
    }
    @PostMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody PhysicalEntity physicalEntity, BindingResult result, Principal principal) {
        userValidator.validate(physicalEntity, result);
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;
        physicalEntityService.update(physicalEntity, principal.getName());
        return new ResponseEntity<PhysicalEntity>(physicalEntity, HttpStatus.CREATED);
    }
    @GetMapping("/findAll")
    public List<PhysicalEntity> findAll(Principal principal) {
        return physicalEntityService.findAll(principal.getName());
    }
    @GetMapping("/find/{id}")
    private ResponseEntity<?> findUserById(@PathVariable Long id, Principal principal) {
        PhysicalEntity physicalEntity = physicalEntityService.findById(id);
        return new ResponseEntity<PhysicalEntity>(physicalEntity, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Long id, Principal principal){
        physicalEntityService.delete(id,principal.getName());
    }
}