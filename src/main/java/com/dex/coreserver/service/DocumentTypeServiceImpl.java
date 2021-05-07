package com.dex.coreserver.service;

import com.dex.coreserver.model.DocumentType;
import com.dex.coreserver.repository.DocumentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentTypeServiceImpl implements DocumentTypeService{

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Override
    public DocumentType create(DocumentType newDocumentType, String username) {
        return documentTypeRepository.save(newDocumentType);
    }

    @Override
    public DocumentType update(DocumentType updatedDocumentType, String username) {
        return documentTypeRepository.save(updatedDocumentType);
    }

    @Override
    public void delete(Long id, String username) {
        documentTypeRepository.deleteById(id);
    }

    @Override
    public List<DocumentType> findAll(String username) {
        return documentTypeRepository.findAll();
    }

    @Override
    public DocumentType findById(Long id) {
        return documentTypeRepository.findById(id).get();
    }
}
