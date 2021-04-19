package com.dex.coreserver.service;

import com.dex.coreserver.model.Document;

public interface DocumentService extends BasicService<Document> {
    Document createDocumentWithCase(Document document, String username
    );
}
