package com.dex.coreserver.controller;

import com.dex.coreserver.model.DocumentMovement;
import com.dex.coreserver.service.DocumentMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/documentMovement")
public class DocumentMovementController {

    @Autowired
    private DocumentMovementService documentMovementService;

    @GetMapping("/findAllDocumentMovements")
    public ResponseEntity<?> findAllDocumentMovements(Principal principal) {
        List<DocumentMovement> documentMovementList = documentMovementService.findAllDocumentMovements(principal.getName());
        return new ResponseEntity<List<DocumentMovement>>(documentMovementList, HttpStatus.OK);
    }

    @GetMapping("/findDocumentMovementByDocumentId/{id}")
    public ResponseEntity<?> findCaseMovementByCaseId(@PathVariable Long id) {
        List<DocumentMovement> documentMovementList = documentMovementService.findByDocumentId(id);
        return new ResponseEntity<List<DocumentMovement>>(documentMovementList, HttpStatus.OK);
    }

    @PatchMapping("/acceptDocument/{id}")
    public ResponseEntity<?> acceptDocument(@PathVariable Long id, Principal principal) {
        DocumentMovement documentMovement = documentMovementService.acceptDocument(id, principal.getName());
        return new ResponseEntity<DocumentMovement>(documentMovement, HttpStatus.OK);
    }

}
