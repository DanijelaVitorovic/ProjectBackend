package com.dex.coreserver.controller;

import com.dex.coreserver.model.Case;
import com.dex.coreserver.model.Document;
import com.dex.coreserver.service.CaseService;
import com.dex.coreserver.service.DocumentService;
import com.dex.coreserver.service.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/create")
    public ResponseEntity<?> createNewDocument(@Valid @RequestBody Document document, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);

        if(errorMap  != null)
            return errorMap;

        Document createdDocument = documentService.create(document, principal.getName());
        return new ResponseEntity<Document>(createdDocument, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateDocument(@Valid @RequestBody Document document, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);

        if(errorMap  != null)
            return errorMap;

        Document updatedDocument = documentService.update(document, principal.getName());
        return new ResponseEntity<Document>(updatedDocument,  HttpStatus.CREATED);
    }

    @GetMapping("/find/{document_id}")
    public ResponseEntity<?> findById(@PathVariable Long document_id) {
        Document document = documentService.findById(document_id);
        return new ResponseEntity<Document>(document, HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(Principal principal) {
        List<Document> documents = documentService.findAll(principal.getName());
        return new ResponseEntity<List<Document>>(documents, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{document_id}")
    public ResponseEntity<?> delete(@PathVariable Long document_id, Principal principal) {
        documentService.delete(document_id, principal.getName());
        return new ResponseEntity<String>("Document deleted", HttpStatus.OK);
    }

    @PostMapping("/createDocumentWithCase")
    public ResponseEntity<?> createDocumentWithCase(@Valid @RequestBody Document document, BindingResult result, Principal principal) {
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);

        if(errorMap  != null)
            return errorMap;

        Document createdDocument = documentService.createDocumentWithCase(document, principal.getName());
        return new ResponseEntity<Document>(createdDocument, HttpStatus.CREATED);
    }
}
