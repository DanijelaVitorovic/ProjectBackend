package com.dex.coreserver.controller;

import com.dex.coreserver.model.LegalEntity;
import com.dex.coreserver.model.User;
import com.dex.coreserver.service.LegalEntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/legalEntity")
public class LegalEntityController {

    @Autowired
    private LegalEntityServiceImpl legalEntityService;

    @PostMapping("/create")
    public ResponseEntity<?> createNewEntity(@Valid @RequestBody LegalEntity legalEntity, @PathVariable String username){
        LegalEntity legalEntity1 = legalEntityService.create(legalEntity, username);
       return new ResponseEntity<LegalEntity>(legalEntity1, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEntity(@PathVariable Long id) {
        LegalEntity legalEntity = legalEntityService.findById(id);
        return new ResponseEntity<LegalEntity>( legalEntity, HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public List<LegalEntity> findAll(LegalEntity legalEntity) {
        List<LegalEntity> legalEntities = legalEntityService.findAll(legalEntity.getEmail());
        return legalEntities;
    }

    @GetMapping("/find/{id}")
    private ResponseEntity<?> findLegalEntityById(@PathVariable Long id) {
        LegalEntity legalEntity = legalEntityService.findById(id);
        return new ResponseEntity<LegalEntity>(legalEntity, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteLegalEntity(@PathVariable Long id, Principal principal){
        legalEntityService.delete(id,principal.getName());
    }

}
