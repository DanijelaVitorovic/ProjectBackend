package com.dex.coreserver.service;

import com.dex.coreserver.model.Document;

import java.util.List;

public interface DocumentService extends BasicService<Document> {
    public List<Document> findDocumentByCaseId(Long id);
}
