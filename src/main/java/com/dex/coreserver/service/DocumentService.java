package com.dex.coreserver.service;

import com.dex.coreserver.model.Document;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DocumentService extends BasicService<Document> {


    Document verificationDocument(Document document, String name);

    Document singingDocument(Document document, String name);

    Document singedDocument(Document document, String name);

    Document finalDocument(Document document, String name);

    public List<Document> findDocumentByCaseId(Long id);

    Document createDocumentWithCase(Document document, String username);

    Page<Document> getAllDocuments(Integer pageNo, Integer pageSize, String sortBy);
}
