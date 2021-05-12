package com.dex.coreserver.repository;

import com.dex.coreserver.model.DocumentTypeAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentTypeAttachmentRepository extends JpaRepository<DocumentTypeAttachment, Long> {
    List<DocumentTypeAttachment> findAllByDocumentType(Long id);
}
