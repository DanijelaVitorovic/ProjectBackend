package com.dex.coreserver.service;


import com.dex.coreserver.model.*;
import com.dex.coreserver.model.enums.DocumentMovementStatement;
import com.dex.coreserver.model.enums.DocumentStatement;
import com.dex.coreserver.model.enums.DocumentStatus;
import com.dex.coreserver.repository.*;
import com.dex.coreserver.utils.DocumentUtils;
import com.dex.coreserver.model.Case;
import com.dex.coreserver.model.Document;
import com.dex.coreserver.model.Employee;
import com.dex.coreserver.model.*;
import com.dex.coreserver.model.enums.DocumentStatus;
import com.dex.coreserver.repository.CaseRepository;
import com.dex.coreserver.repository.DocumentAttachmentRepository;
import com.dex.coreserver.repository.DocumentRepository;
import com.dex.coreserver.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class DocumentServiceImpl implements DocumentService{

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CaseRepository caseRepository;

    @Autowired
    private CaseService caseService;

    @Autowired
    private DocumentAttachmentServiceImpl documentAttachmentService;

    @Autowired
    private DocumentAttachmentRepository documentAttachmentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DocumentMovementRepository documentMovementRepository;

    @Autowired
    private DocumentMovementService documentMovementService;

    @Autowired
    private DocuemntClassificationService docuemntClassificationService;


    @Override
    public Document create(Document document, String username) {
        Employee employee = employeeRepository.findById(document.getEmployeeCreated().getId()).get();
        if(employee == null)
            throw new RuntimeException("Zaposleni ne psotoji");
        document.setEmployeeCreated(employee);

        Case _case = caseRepository.findById(document.get_case().getId()).get();
        if(_case == null)
            throw new RuntimeException("Slucaj ne postoji");
        document.set_case(_case);

        DocumentClassification documentClassification = docuemntClassificationService.findById(document.getDocumentClassification().getId());
        if(documentClassification == null)
            throw new RuntimeException("Klasifikacija ne postoji");
        document.setDocumentClassification(documentClassification);

        return documentRepository.save(document);
    }

    @Override
    public Document update(Document document, String username) {
        Employee employee = employeeRepository.findById(document.getEmployeeCreated().getId()).get();
        document.setEmployeeCreated(employee);

        Case _case = caseRepository.findById(document.get_case().getId()).get();
        document.set_case(_case);

        DocumentClassification documentClassification = docuemntClassificationService.findById(document.getDocumentClassification().getId());
        document.setDocumentClassification(documentClassification);

        return documentRepository.save(document);
    }

    @Override
    public void delete(Long id, String username) {
        documentRepository.deleteById(id);
    }

    @Override
    public List<Document> findAll(String username) {
        return documentRepository.findAll();
    }

    @Override
    public Document findById(Long id) {
        return documentRepository.findById(id).get();
    }

    @Override
    public List<Document> findDocumentByCaseId(Long id) {

        Case newCase = caseRepository.findById(id).get();
        return documentRepository.findBy_case(newCase);
    }

    @Override
    public Document createDocumentWithCase(Document document, String username) {
        Case newCase= document.get_case();
        Case createdCase = caseService.create(newCase, username);
        document.set_case(createdCase);
        Document createdDocument = create(document, username);
        createdDocument.setDocumentStatus(DocumentStatus.PROCEEDING);
        return createdDocument;
    }

    @Override
    public Page<Document> getAllDocuments(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Document> pagedResult = documentRepository.findAll(paging);
        return pagedResult;
    }

    @Override
    public Document verificationDocument(Document document, String name) {
        if(document == null) {
            throw new RuntimeException("Dokument ne postoji!");
        }
        if(!document.getDocumentStatus().equals(DocumentStatus.PROCEEDING))
            throw new RuntimeException("Dokument nije u statusu podnosenje");

        Document verifiedDocument = document;
        verifiedDocument.setDocumentStatus(DocumentStatus.VERIFICATION);
        verifiedDocument.setVerificationDate(new Date());
        verifiedDocument.setVerificationEmployee(verifiedDocument.getVerificationEmployee());
        return update(verifiedDocument, name);
    }

    @Override
    public Document singingDocument(Document singingDocument, String name) {
        if(singingDocument == null) {
            throw new RuntimeException("Dokument ne postoji!");
        }
        if(!singingDocument.getDocumentStatus().equals(DocumentStatus.VERIFICATION))
            throw new RuntimeException("Dokument nije verifikovan!");

        singingDocument.setDocumentStatus(DocumentStatus.SIGNING);
        singingDocument.setSigningEmployee(singingDocument.getSigningEmployee());
        singingDocument.setSigningDate(new Date());
        return update(singingDocument, name);
    }

    @Override
    public Document singedDocument(Document singedDocument, String name) {
        if(singedDocument == null) {
            throw new RuntimeException("Dokument ne postoji!");
        }

        if( !singedDocument.getDocumentStatus().equals(DocumentStatus.SIGNING)) {
            throw new RuntimeException("Dokument nije u statusu potpisivanje!");
        }

        singedDocument.setDocumentStatus(DocumentStatus.SIGNED);
        singedDocument.setSignedEmployee(singedDocument.getSignedEmployee());
        singedDocument.setSignedDate(new Date());
        return update(singedDocument, name);
    }

    @Override
    public Document finalDocument(Document finalDocument, String name) {
        if(finalDocument == null) {
            throw new RuntimeException("Dokument ne postoji!");
        }

        if( !finalDocument.getDocumentStatus().equals(DocumentStatus.SIGNED))
            throw new RuntimeException("Dokument nije potpisan!");

        finalDocument.setDocumentStatus(DocumentStatus.FINAL);
        finalDocument.setFinalEmployee(finalDocument.getFinalEmployee());
        finalDocument.setFinalDate(new Date());
        return update(finalDocument, name);
    }

    @Transactional
    @Override
    public DocumentMovement acceptVerification(DocumentMovement documentMovement, String username) {

        if(documentMovement.getDocument() == null || documentRepository.findById(documentMovement.getDocument().getId()) == null)
            throw new RuntimeException("Документ не постоји.");

        Document updateDocument= findById(documentMovement.getDocument().getId());

        if(documentMovementRepository.findDocumentMovementByDocumentAndStateSent(updateDocument, DocumentMovementStatement.SENT) != null) {
            List<DocumentMovement> documentMovementList = documentMovementRepository.findAll();

            for(DocumentMovement dm : documentMovementList) {
                if(dm.getDocument().getId() == updateDocument.getId())
                    throw new RuntimeException("Ваш документ је у стању: " + dm.getDocumentMovementStatement());
            }
        }

        User foundUser= userService.findUserByUsername(username);
        Employee foundEmployee= employeeService.findEmployeeByUser(foundUser);

        updateDocument.setDocumentStatement(DocumentStatement.ASSIGN);

        documentMovement.setVerificationEmployee(documentMovement.getVerificationEmployee());
        documentMovement.setVerificationDate(new Date());

        documentMovement.setEmployeeSend(foundEmployee);
        documentMovement.setEmployeeReceived(documentMovement.getVerificationEmployee());

        documentMovement.setDocument(updateDocument);
        documentMovement.setDocumentMovementStatement(DocumentMovementStatement.SENT);
        update(updateDocument, username);

        return documentMovementService.create(documentMovement, username);
    }

    @Transactional
    @Override
    public DocumentMovement acceptSinging(DocumentMovement documentMovement, String username) {

        if(documentMovement.getDocument() == null || documentRepository.findById(documentMovement.getDocument().getId()) == null)
            throw new RuntimeException("Документ не постоји.");

        Document updateDocument= findById(documentMovement.getDocument().getId());

        if(documentMovementRepository.findDocumentMovementByDocumentAndStateSent(updateDocument, DocumentMovementStatement.SENT) != null) {
            List<DocumentMovement> documentMovementList = documentMovementRepository.findAll();

            for(DocumentMovement dm : documentMovementList) {
                if(dm.getDocument().getId() == updateDocument.getId())
                    throw new RuntimeException("Ваш документ је у стању: " + dm.getDocumentMovementStatement());
            }
        }

        User foundUser= userService.findUserByUsername(username);
        Employee foundEmployee= employeeService.findEmployeeByUser(foundUser);

        updateDocument.setDocumentStatement(DocumentStatement.ASSIGN);

        documentMovement.setSigningEmployee(foundEmployee);
        documentMovement.setSigningDate(new Date());

        documentMovement.setEmployeeSend(foundEmployee);
        documentMovement.setEmployeeReceived(documentMovement.getSigningEmployee());

        documentMovement.setDocument(updateDocument);
        documentMovement.setDocumentMovementStatement(DocumentMovementStatement.SENT);
        update(updateDocument, username);

        return documentMovementService.create(documentMovement, username);
    }

    @Transactional
    @Override
    public DocumentMovement acceptSinged(DocumentMovement documentMovement, String username) {

        if(documentMovement.getDocument() == null || documentRepository.findById(documentMovement.getDocument().getId()) == null)
            throw new RuntimeException("Документ не постоји.");

        Document updateDocument= findById(documentMovement.getDocument().getId());

        if(documentMovementRepository.findDocumentMovementByDocumentAndStateSent(updateDocument, DocumentMovementStatement.SENT) != null) {
            List<DocumentMovement> documentMovementList = documentMovementRepository.findAll();

            for(DocumentMovement dm : documentMovementList) {
                if(dm.getDocument().getId() == updateDocument.getId())
                    throw new RuntimeException("Ваш документ је у стању: " + dm.getDocumentMovementStatement());
            }
        }

        User foundUser= userService.findUserByUsername(username);
        Employee foundEmployee= employeeService.findEmployeeByUser(foundUser);

        updateDocument.setDocumentStatement(DocumentStatement.ASSIGN);

        documentMovement.setSignedEmployee(foundEmployee);
        documentMovement.setSignedDate(new Date());

        documentMovement.setEmployeeSend(foundEmployee);
        documentMovement.setEmployeeReceived(documentMovement.getSignedEmployee());

        documentMovement.setDocument(updateDocument);
        documentMovement.setDocumentMovementStatement(DocumentMovementStatement.SENT);
        update(updateDocument, username);

        return documentMovementService.create(documentMovement, username);
    }

    @Transactional
    @Override
    public DocumentMovement acceptFinal(DocumentMovement documentMovement, String username) {

        if(documentMovement.getDocument() == null || documentRepository.findById(documentMovement.getDocument().getId()) == null)
            throw new RuntimeException("Документ не постоји.");

        Document updateDocument= findById(documentMovement.getDocument().getId());

        if(documentMovementRepository.findDocumentMovementByDocumentAndStateSent(updateDocument, DocumentMovementStatement.SENT) != null) {
            List<DocumentMovement> documentMovementList = documentMovementRepository.findAll();

            for(DocumentMovement dm : documentMovementList) {
                if(dm.getDocument().getId() == updateDocument.getId())
                    throw new RuntimeException("Ваш документ је у стању: " + dm.getDocumentMovementStatement());
            }
        }

        User foundUser= userService.findUserByUsername(username);
        Employee foundEmployee= employeeService.findEmployeeByUser(foundUser);

        updateDocument.setDocumentStatement(DocumentStatement.ASSIGN);

        documentMovement.setFinalEmployee(foundEmployee);
        documentMovement.setFinalDate(new Date());

        documentMovement.setEmployeeSend(foundEmployee);
        documentMovement.setEmployeeReceived(documentMovement.getFinalEmployee());

        documentMovement.setDocument(updateDocument);
        documentMovement.setDocumentMovementStatement(DocumentMovementStatement.SENT);
        update(updateDocument, username);

        return documentMovementService.create(documentMovement, username);
    }

    @Transactional
    @Override
    public DocumentMovement revokeDocumentMovement(Document updateDocument, String username) throws Exception {
        if(documentMovementService.findDocumentMovementByDocumentAndStateSent(updateDocument, DocumentMovementStatement.SENT) == null) {
            throw new Exception("Не постоји захтев за повлачење!");
        }

        DocumentMovement documentMovement = documentMovementService.findDocumentMovementByDocumentAndStateSent(updateDocument, DocumentMovementStatement.SENT);
        updateDocument.setDocumentStatement(DocumentStatement.REVOKE);
        documentMovement.setDocumentMovementStatement(DocumentMovementStatement.REVOKED);
        update(updateDocument, username);
        return documentMovementService.create(documentMovement, username);
    }

}
