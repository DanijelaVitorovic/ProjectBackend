package com.dex.coreserver.service;

import com.dex.coreserver.model.DocumentType;

import java.util.List;

public interface DocumentTypeService {
    DocumentType create(DocumentType newDocumentType, String username);
    DocumentType update(DocumentType updatedDocumentType, String username);
    void delete(Long id, String username);
    List<DocumentType> findAll(String username);
    DocumentType findById(Long id);
}
