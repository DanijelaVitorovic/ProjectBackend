package com.dex.coreserver.controller;

import com.dex.coreserver.model.DocumentType;
import com.dex.coreserver.service.DocumentTypeService;
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
@RequestMapping("/api/documentType")
public class DocumentTypeContoller {

    @Autowired
    DocumentTypeService documentTypeService;

    @Autowired
    MapValidationErrorService mapValidationErrorService;

    @PostMapping("/createDocumentType")
    public ResponseEntity<?> create(@Valid @RequestBody DocumentType documentTypeForCreate, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;
        DocumentType newDocumentType = documentTypeService.create(documentTypeForCreate, principal.getName());
        return new ResponseEntity<DocumentType>(newDocumentType, HttpStatus.CREATED);
    }

    @PostMapping("/updateDocumentType")
    public ResponseEntity<?> update(@Valid @RequestBody DocumentType documentTypeForUpdate, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;
        DocumentType updatedDocumentType =  documentTypeService.update(documentTypeForUpdate, principal.getName());
        return new ResponseEntity<DocumentType>(updatedDocumentType, HttpStatus.CREATED);
    }

    @GetMapping("/findAllDocumentTypes")
    public List<DocumentType> findAll(Principal principal) {
        return documentTypeService.findAll(principal.getName());
    }

    @GetMapping("/findDocumentType/{id}")
    private ResponseEntity<?> findDocumentTypeById(@PathVariable Long id, Principal principal) {
        DocumentType foundDocumentType = documentTypeService.findById(id);
        return new ResponseEntity<DocumentType>(foundDocumentType, HttpStatus.OK);
    }

    @DeleteMapping("/deleteDocumentType/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Principal principal) {
        documentTypeService.delete(id, principal.getName());
        return new ResponseEntity<String>("Document Type deleted", HttpStatus.OK);
    }
}
