package com.dex.coreserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class DocumentTypeAttachment extends AbstractDataModel{

    private String documentName;
    private String uuidDocName;
    private String mimeType;
    private String mimeTypeShort;
    @Transient
    private byte[] documentTypeContent;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DocumentType")
    private DocumentType documentType;
}
