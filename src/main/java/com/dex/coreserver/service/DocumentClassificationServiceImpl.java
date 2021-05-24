package com.dex.coreserver.service;

import com.dex.coreserver.model.Document;
import com.dex.coreserver.model.DocumentClassification;
import com.dex.coreserver.repository.DocumentClassificationRepository;
import com.dex.coreserver.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentClassificationServiceImpl implements DocuemntClassificationService{

    @Autowired
    private DocumentClassificationRepository documentClassificationRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public DocumentClassification create(DocumentClassification documentClassification, String username) {
        return documentClassificationRepository.save(documentClassification);
    }

    @Override
    public DocumentClassification update(DocumentClassification documentClassification, String username) {
        return documentClassificationRepository.save(documentClassification);
    }

    @Override
    public void delete(Long id, String username) {
        documentClassificationRepository.deleteById(id);
    }

    @Override
    public List<DocumentClassification> findAll(String username) {
        return documentClassificationRepository.findAll();
    }

    @Override
    public DocumentClassification findById(Long id) {
        return documentClassificationRepository.findById(id).get();
    }
}
