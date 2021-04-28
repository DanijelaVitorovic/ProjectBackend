package com.dex.coreserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentAttachment extends AbstractDataModel {
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "documentId")
    private Document document;

    private String documentName;
    private String uuidDocName;
    private String mimeType;
    private String mimeTypeShort;
    @Transient
    private byte[] documentContent;
}
