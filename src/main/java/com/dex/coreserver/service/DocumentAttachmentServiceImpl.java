package com.dex.coreserver.service;

import com.dex.coreserver.model.Document;
import com.dex.coreserver.model.DocumentAttachment;
import com.dex.coreserver.repository.DocumentAttachmentRepository;
import com.dex.coreserver.repository.DocumentRepository;
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
public class DocumentAttachmentServiceImpl implements DocumentAttachmentService{

    @Autowired
    private DocumentAttachmentRepository documentAttachmentRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public DocumentAttachment create(DocumentAttachment documentAttachment, String username) {
        return documentAttachmentRepository.save(documentAttachment);
    }

    @Override
    public DocumentAttachment update(DocumentAttachment documentAttachment, String username) {
        return documentAttachmentRepository.save(documentAttachment);
    }

    @Override
    public void delete(Long id, String username) {
        documentAttachmentRepository.deleteById(id);
    }

    @Override
    public void deleteByDocumnet(Long documentId, Long id, String username) {
        Document document = documentRepository.findById(documentId).get();
        if(document == null)
            throw new RuntimeException("Dokument ne postoji");

        DocumentAttachment documentAttachment = documentAttachmentRepository.findById(id).get();
        if(documentAttachment.getDocument().getId() != documentId)
            throw new RuntimeException("Trazeni objekat ne postoji u dokumentu");
        documentAttachmentRepository.deleteById(id);
    }

    @Override
    public List<DocumentAttachment> findAll(String username) {
        return documentAttachmentRepository.findAll();
    }

    @Override
    public DocumentAttachment findById(Long id) {
        return documentAttachmentRepository.findById(id).get();
    }

    @Override
    public DocumentAttachment findByDocumentName(String documentName) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(ApplicationUtil.getFilePath()+ documentName);
            DocumentAttachment documentAttachment = new DocumentAttachment();
            documentAttachment.setDocumentContent(DocumentUtils.transformInputStreamInByteArray(inputStream));

            return documentAttachment;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DocumentAttachment> findAllByDocument(Long id, String username) {
        List<DocumentAttachment> allDocumentAttachments = documentAttachmentRepository.findAll();
        List<DocumentAttachment> documentAttachments = new LinkedList<DocumentAttachment>();

        for(DocumentAttachment attachment: allDocumentAttachments) {
            if(attachment.getDocument().get_case().getId() == id) {
                documentAttachments.add(attachment);
            }
        }
        return documentAttachments;
    }

    @Override
    public void upload(MultipartFile uploadFile, Long id, String username) {
        DocumentAttachment documentAttachment = new DocumentAttachment();

        documentAttachment.setDocumentName(uploadFile.getOriginalFilename());
        documentAttachment.setMimeType(uploadFile.getContentType());

        String randomUUIDString = DocumentUtils.getUUIDandMymeTypeShort(uploadFile)[0];
        documentAttachment.setUuidDocName(randomUUIDString);

        documentAttachment.setMimeTypeShort(DocumentUtils.getUUIDandMymeTypeShort(uploadFile)[1]);
        DocumentUtils.saveDocumentOnDisc(randomUUIDString, uploadFile);

        Document document = documentRepository.findById(id).get();
        if(document == null)
            throw new RuntimeException("Dokument ne postoji!");
        documentAttachment.setDocument(document);

        documentAttachmentRepository.save(documentAttachment);
        DocumentUtils.saveDocumentOnDisc(randomUUIDString, uploadFile);
    }
}
