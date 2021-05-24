package com.dex.coreserver.service;

import com.dex.coreserver.model.*;
import com.dex.coreserver.model.enums.DocumentStatus;
import com.dex.coreserver.repository.CaseRepository;
import com.dex.coreserver.repository.DocumentAttachmentRepository;
import com.dex.coreserver.repository.DocumentRepository;
import com.dex.coreserver.repository.EmployeeRepository;
import com.dex.coreserver.utils.DocumentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
}
