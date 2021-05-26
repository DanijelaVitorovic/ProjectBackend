package com.dex.coreserver.service;

import com.dex.coreserver.model.Document;
import com.dex.coreserver.model.DocumentMovement;
import com.dex.coreserver.model.Employee;
import com.dex.coreserver.model.User;
import com.dex.coreserver.model.enums.DocumentMovementStatement;
import com.dex.coreserver.model.enums.DocumentStatement;
import com.dex.coreserver.model.enums.DocumentStatus;
import com.dex.coreserver.repository.DocumentMovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class DocumentMovementServiceImpl implements DocumentMovementService{

    @Autowired
    private DocumentMovementRepository documentMovementRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DocumentService documentService;

    @Override
    public DocumentMovement create(DocumentMovement documentMovement, String username) {
        return documentMovementRepository.save(documentMovement);
    }

    @Override
    public DocumentMovement update(DocumentMovement documentMovement, String username) {
        return documentMovementRepository.save(documentMovement);
    }

    @Override
    public void delete(Long id, String username) {
    }

    @Override
    public List<DocumentMovement> findAll(String username) {
        return documentMovementRepository.findAll();
    }

    @Override
    public DocumentMovement findById(Long id) {
        return documentMovementRepository.findById(id).get();
    }

    @Override
    public List<DocumentMovement> findAllDocumentMovements(String username) {

        User user = userService.findUserByUsername(username);
        Employee foundEmployee= employeeService.findEmployeeByUser(user);
        List<DocumentMovement> documentMovementList= documentMovementRepository.findEmployeeByUserAndStateSent(foundEmployee, DocumentMovementStatement.SENT);

        return documentMovementList;
    }

    @Override
    public List<DocumentMovement> findByDocumentId(Long id) {
        List<DocumentMovement> documentMovementList = documentMovementRepository.findAll();
        List<DocumentMovement> documentMovementsByDocument = new LinkedList<>();

        for(DocumentMovement dm: documentMovementList) {
            if(dm.getDocument().getId() == id) {
                documentMovementsByDocument.add(dm);
            }
        }
        return documentMovementsByDocument;
    }

    @Override
    public DocumentMovement acceptDocument(Long id, String username) {
        DocumentMovement documentMovement=documentMovementRepository.findById(id).get();

        documentMovement.setDocumentMovementStatement(DocumentMovementStatement.RECEIVED);
        Document acceptedDocument= documentMovement.getDocument();

        User foundUser= userService.findUserByUsername(username);
        Employee foundEmployee= employeeService.findEmployeeByUser(foundUser);

        documentMovement.setEmployeeReceived(foundEmployee);

        if(documentMovement.getVerificationEmployee()!=null){
            acceptedDocument.setDocumentStatus(DocumentStatus.VERIFICATION);
            acceptedDocument.setVerificationEmployee(documentMovement.getVerificationEmployee());
            acceptedDocument.setVerificationDate(new Date());
        }

        if(documentMovement.getSigningEmployee()!=null){
            acceptedDocument.setDocumentStatus(DocumentStatus.SIGNING);
            acceptedDocument.setSigningEmployee(documentMovement.getSigningEmployee());
            acceptedDocument.setSignedDate(new Date());}

        if(documentMovement.getSignedEmployee()!=null){
            acceptedDocument.setDocumentStatus(DocumentStatus.SIGNED);
            acceptedDocument.setSignedEmployee(documentMovement.getSignedEmployee());
            acceptedDocument.setSignedDate(new Date()); }

        if(documentMovement.getFinalEmployee()!=null){
            acceptedDocument.setDocumentStatus(DocumentStatus.FINAL);
            acceptedDocument.setFinalEmployee(documentMovement.getFinalEmployee());
            acceptedDocument.setFinalDate(new Date()); }

        documentService.update(acceptedDocument, username);
        documentMovement.setDocument(acceptedDocument);

        return create(documentMovement, username);
    }

    @Override
    public DocumentMovement findDocumentMovementByDocumentAndStateSent( Document updateDocument, DocumentMovementStatement sent) {
        return documentMovementRepository.findDocumentMovementByDocumentAndStateSent(updateDocument, sent);
    }
}
