package com.dex.coreserver.service;

import com.dex.coreserver.model.DocumentAttachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentAttachmentService extends BasicService<DocumentAttachment> {
    void delete(Long id, String username);

    void deleteByDocumnet(Long documentId, Long id, String username);

    DocumentAttachment findByDocumentName(String name);

    List<DocumentAttachment> findAllByDocument(Long id, String username);

    void upload(MultipartFile uploadFile, Long id, String name);
}