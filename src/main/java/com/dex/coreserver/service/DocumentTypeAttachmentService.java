package com.dex.coreserver.service;

import com.dex.coreserver.model.DocumentTypeAttachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentTypeAttachmentService extends BasicService<DocumentTypeAttachment> {

    void deleteByDocumentType(Long documentTypeId, Long id, String username);

    DocumentTypeAttachment findByDocumentTypeName(String documentTypeName);

    List<DocumentTypeAttachment> findAllByDocumentType(Long id, String username);

    void upload(MultipartFile uploadFile, Long id, String username);
}
