package com.dex.coreserver.controller;

import com.dex.coreserver.model.Document;
import com.dex.coreserver.model.DocumentAttachment;
import com.dex.coreserver.service.DocumentAttachmentService;
import com.dex.coreserver.service.MapValidationErrorService;
import org.apache.logging.log4j.message.LoggerNameAwareMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.Doc;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/documentAttachment")
public class DocumentAttachmentController {

    @Autowired
    public DocumentAttachmentService documentAttachmentService;

    @Autowired
    public MapValidationErrorService mapValidationErrorService;

    @PostMapping("/upload/{id}")
    public ResponseEntity<?> uploadDocumentAttachment(@RequestParam("file") MultipartFile uploadFile,@PathVariable Long id, Principal principal) {
        DocumentAttachment createDocumentAttachment = documentAttachmentService.upload(uploadFile, id, principal.getName());
        return new ResponseEntity<DocumentAttachment>(createDocumentAttachment, HttpStatus.CREATED);
    }

    @GetMapping("/find/{documentName}")
    public ResponseEntity<?> findByDocumentName(@PathVariable String documentName) {
        DocumentAttachment documentAttachment = documentAttachmentService.findByDocumentName(documentName);
        return new ResponseEntity<DocumentAttachment>(documentAttachment, HttpStatus.OK);
    }

    @GetMapping("/findAllByDocument/{id}")
    public ResponseEntity<?> findAllByDocument(@PathVariable Long id, Principal principal) {
        List<DocumentAttachment> documentAttachments = documentAttachmentService.findAllByDocument(id, principal.getName());
        return new ResponseEntity<List<DocumentAttachment>>(documentAttachments, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public  ResponseEntity<?> delete(@PathVariable Long id, Principal principal) {
        documentAttachmentService.delete(id, principal.getName());
        return new ResponseEntity<String>("Obrisan je!", HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(Principal principal) {
        return new ResponseEntity<List<DocumentAttachment>>(documentAttachmentService.findAll(principal.getName()), HttpStatus.OK);
    }

}
