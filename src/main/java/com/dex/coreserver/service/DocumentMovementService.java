package com.dex.coreserver.service;

import com.dex.coreserver.model.Document;
import com.dex.coreserver.model.DocumentMovement;
import com.dex.coreserver.model.enums.DocumentMovementStatement;

import java.util.List;

public interface DocumentMovementService extends BasicService<DocumentMovement> {
    List<DocumentMovement> findAllDocumentMovements(String name);

    List<DocumentMovement> findByDocumentId(Long id);

    DocumentMovement acceptDocument(Long id, String name);

    DocumentMovement findDocumentMovementByDocumentAndStateSent(Document updateDocument, DocumentMovementStatement sent);
}
