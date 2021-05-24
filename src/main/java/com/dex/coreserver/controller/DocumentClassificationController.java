package com.dex.coreserver.controller;

import com.dex.coreserver.model.DocumentClassification;
import com.dex.coreserver.service.DocuemntClassificationService;
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
@RequestMapping("/api/documentClassification")
public class DocumentClassificationController {

    @Autowired
    private DocuemntClassificationService docuemntClassificationService;

    @Autowired
    MapValidationErrorService mapValidationErrorService;

    @PostMapping("/createDocumentClassification")
    public ResponseEntity<?> create(@Valid @RequestBody DocumentClassification documentClassification, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;

        DocumentClassification createdDocumentClassification = docuemntClassificationService.create(documentClassification, principal.getName());
        return new ResponseEntity<DocumentClassification>(createdDocumentClassification, HttpStatus.CREATED);
    }

    @PostMapping("/updateDocumentClassification")
    public ResponseEntity<?> update(@Valid @RequestBody DocumentClassification documentClassification, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;

        DocumentClassification updatedDocumentClassification = docuemntClassificationService.update(documentClassification, principal.getName());
        return new ResponseEntity<DocumentClassification>(updatedDocumentClassification, HttpStatus.CREATED);
    }

    @GetMapping("/findAllDocumentClassifications")
    public ResponseEntity<?> findAll(Principal principal) {
        List<DocumentClassification> documentClassificationList = docuemntClassificationService.findAll(principal.getName());
        return new ResponseEntity<List<DocumentClassification>>(documentClassificationList, HttpStatus.OK);
    }

    @GetMapping("/findDocumentClassificationById/{id}")
    private ResponseEntity<?> findDocumentClassificationById(@PathVariable Long id, Principal principal) {
        DocumentClassification documentClassification = docuemntClassificationService.findById(id);
        return new ResponseEntity<DocumentClassification>(documentClassification, HttpStatus.OK);
    }

    @DeleteMapping("/deleteDocumentClassification/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Principal principal) {
        docuemntClassificationService.delete(id, principal.getName());
        return new ResponseEntity<String>("DocumentClassification deleted", HttpStatus.OK);
    }
}
