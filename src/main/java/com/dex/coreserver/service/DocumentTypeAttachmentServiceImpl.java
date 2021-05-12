package com.dex.coreserver.service;

import com.dex.coreserver.model.DocumentType;
import com.dex.coreserver.model.DocumentTypeAttachment;
import com.dex.coreserver.repository.DocumentTypeAttachmentRepository;
import com.dex.coreserver.repository.DocumentTypeRepository;
import com.dex.coreserver.utils.ApplicationUtil;
import com.dex.coreserver.utils.DocumentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

@Service
public class DocumentTypeAttachmentServiceImpl implements DocumentTypeAttachmentService{

    @Autowired
    private DocumentTypeAttachmentRepository documentTypeAttachmentRepository;

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Override
    public DocumentTypeAttachment create(DocumentTypeAttachment documentTypeAttachment, String username) {
        return documentTypeAttachmentRepository.save(documentTypeAttachment);
    }

    @Override
    public DocumentTypeAttachment update(DocumentTypeAttachment documentTypeAttachment, String username) {
        return documentTypeAttachmentRepository.save(documentTypeAttachment);
    }

    @Override
    public void delete(Long id, String username) {
        documentTypeAttachmentRepository.deleteById(id);
    }

    @Override
    public List<DocumentTypeAttachment> findAll(String username) {
        return documentTypeAttachmentRepository.findAll();
    }

    @Override
    public DocumentTypeAttachment findById(Long id) {
        return documentTypeAttachmentRepository.findById(id).get();
    }

    @Override
    public void deleteByDocumentType(Long documentTypeId, Long id, String username) {
        DocumentType documentType = documentTypeRepository.findById(documentTypeId).get();
        if(documentType == null)
            throw new RuntimeException("Tip dokument ne postoji");

        DocumentTypeAttachment documentTypeAttachment = documentTypeAttachmentRepository.findById(id).get();
        if(documentTypeAttachment.getDocumentType().getId() != documentTypeId)
            throw new RuntimeException("Trazeni objekat ne postoji u tipu dokumenta");
        documentTypeAttachmentRepository.deleteById(id);
    }

    @Override
    public DocumentTypeAttachment findByDocumentTypeName(String documentName) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(ApplicationUtil.getFilePath()+ documentName);
            DocumentTypeAttachment documentTypeAttachment = new DocumentTypeAttachment();
            documentTypeAttachment.setDocumentTypeContent(DocumentUtils.transformInputStreamInByteArray(inputStream));

            return documentTypeAttachment;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    @Override
    public List<DocumentTypeAttachment> findAllByDocumentType(Long id, String username) {
        List<DocumentTypeAttachment> allDocumentTypeAttachments = documentTypeAttachmentRepository.findAll();
        List<DocumentTypeAttachment> documentTypeAttachments = new LinkedList<DocumentTypeAttachment>();

        for(DocumentTypeAttachment attachment: allDocumentTypeAttachments) {
            if(attachment.getDocumentType().getId() == id) {
                documentTypeAttachments.add(attachment);
            }
        }
        return documentTypeAttachments;
    }

    @Override
    public void upload(MultipartFile uploadFile, Long id, String username) {
        DocumentTypeAttachment documentTypeAttachment = new DocumentTypeAttachment();

        documentTypeAttachment.setDocumentName(uploadFile.getOriginalFilename());
        documentTypeAttachment.setMimeType(uploadFile.getContentType());

        String randomUUIDString = DocumentUtils.getUUIDandMymeTypeShort(uploadFile)[0];
        documentTypeAttachment.setUuidDocName(randomUUIDString);

        documentTypeAttachment.setMimeTypeShort(DocumentUtils.getUUIDandMymeTypeShort(uploadFile)[1]);
        DocumentUtils.saveDocumentOnDisc(randomUUIDString, uploadFile);

        DocumentType documentType = documentTypeRepository.findById(id).get();
        if(documentType == null)
            throw new RuntimeException("Tip dokumenta ne postoji!");
        documentTypeAttachment.setDocumentType(documentType);

        documentTypeAttachmentRepository.save(documentTypeAttachment);
        DocumentUtils.saveDocumentOnDisc(randomUUIDString, uploadFile);
    }
}
