package com.dex.coreserver.service;

import com.dex.coreserver.model.Document;
import com.dex.coreserver.model.DocumentMovement;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;

import javax.transaction.Transactional;
import java.util.List;

public interface DocumentService extends BasicService<Document> {


    Document verificationDocument(Document document, String name);

    Document singingDocument(Document document, String name);

    Document singedDocument(Document document, String name);

    Document finalDocument(Document document, String name);

    public List<Document> findDocumentByCaseId(Long id);

    Document createDocumentWithCase(Document document, String username);

    DocumentMovement acceptVerification(DocumentMovement newCaseMovement, String name);

    DocumentMovement acceptSinging(DocumentMovement newCaseMovement, String name);

    DocumentMovement acceptSinged(DocumentMovement newCaseMovement, String name);

    DocumentMovement acceptFinal(DocumentMovement newCaseMovement, String name);

    @Transactional
    DocumentMovement revokeDocumentMovement(Document updateDocument, String username) throws Exception;

    Page<Document> getAllDocuments(Integer pageNo, Integer pageSize, String sortBy);

}
