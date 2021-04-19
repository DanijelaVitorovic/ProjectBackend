package com.dex.coreserver.service;

import com.dex.coreserver.model.Case;
import com.dex.coreserver.model.Document;
import com.dex.coreserver.model.Employee;
import com.dex.coreserver.repository.CaseRepository;
import com.dex.coreserver.repository.DocumentRepository;
import com.dex.coreserver.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return documentRepository.save(document);
    }

    @Override
    public Document update(Document document, String username) {
        Employee employee = employeeRepository.findById(document.getEmployeeCreated().getId()).get();
        document.setEmployeeCreated(employee);
        Case _case = caseRepository.findById(document.get_case().getId()).get();
        document.set_case(_case);
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
    public Document createDocumentWithCase(Document document, String username) {
        Case newCase= document.get_case();
        Case createdCase = caseService.create(newCase, username);
        document.set_case(createdCase);
        Document createdDocument = create(document, username);
        return createdDocument;
    }
}
