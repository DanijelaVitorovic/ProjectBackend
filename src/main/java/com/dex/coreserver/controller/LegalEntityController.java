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
@RequestMapping("/api/legalentity")
public class LegalEntityController {

    @Autowired
    private LegalEntityServiceImpl legalEntityService;

    @PostMapping("/create")
    public ResponseEntity<?> createNewEntity(@Valid @RequestBody LegalEntity legalEntity, @PathVariable String username){
       return new ResponseEntity<LegalEntity>(legalEntityService.create(legalEntity, username), HttpStatus.CREATED);
    }

    @GetMapping("/{entity_id}")
    public ResponseEntity<?> getEntity(@PathVariable Long entity_id) {
        return new ResponseEntity<LegalEntity>(legalEntityService.findById(entity_id), HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public List<LegalEntity> findAll(LegalEntity legalEntity) {

        return legalEntityService.findAll(legalEntity.getName());
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
