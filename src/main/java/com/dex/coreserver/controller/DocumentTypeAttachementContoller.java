package com.dex.coreserver.controller;

import com.dex.coreserver.model.DocumentTypeAttachment;
import com.dex.coreserver.service.DocumentTypeAttachmentService;
import com.dex.coreserver.service.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/documentTypeAttachment")
public class DocumentTypeAttachementContoller {

    @Autowired
    private DocumentTypeAttachmentService documentTypeAttachmentService;

    @Autowired
    public MapValidationErrorService mapValidationErrorService;

    @PostMapping("/upload/{id}")
    public ResponseEntity<?> uploadDocumentTypeAttachment(@RequestParam("file") MultipartFile uploadFile, @PathVariable Long id, Principal principal) {
        documentTypeAttachmentService.upload(uploadFile, id, principal.getName());
        return new ResponseEntity<String>("Fajl je uploadovan", HttpStatus.CREATED);
    }

    @GetMapping("/find/{documentTypeName}")
    public ResponseEntity<?> findByDocumentTypeName(@PathVariable String documentTypeName) {
        DocumentTypeAttachment documentTypeAttachment = documentTypeAttachmentService.findByDocumentTypeName(documentTypeName);
        return new ResponseEntity<DocumentTypeAttachment>(documentTypeAttachment, HttpStatus.OK);
    }

    @GetMapping("/findAllByDocumentType/{id}")
    public ResponseEntity<?> findAllByDocument(@PathVariable Long id, Principal principal) {
        List<DocumentTypeAttachment> documentTypeAttachmentList = documentTypeAttachmentService.findAllByDocumentType(id, principal.getName());
        return new ResponseEntity<List<DocumentTypeAttachment>>(documentTypeAttachmentList, HttpStatus.OK);
    }

    @DeleteMapping("/deleteDocumentTypeAttachment/{id}")
    public  ResponseEntity<?> deleteDocumentTypeAttachment(@PathVariable Long id, Principal principal) {
        documentTypeAttachmentService.delete(id, principal.getName());
        return new ResponseEntity<String>("Obrisan je!", HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(Principal principal) {
        return new ResponseEntity<List<DocumentTypeAttachment>>(documentTypeAttachmentService.findAll(principal.getName()), HttpStatus.OK);
    }
}
