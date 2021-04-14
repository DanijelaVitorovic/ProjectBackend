package com.dex.coreserver.model;

import com.dex.coreserver.model.enums.DocumentStatus;
import com.dex.coreserver.model.enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Document extends AbstractDataModel{

    private String title;
    private String documentNumber;
    private String externalNumber;
    private String description;
    private DocumentType documentType;
    private DocumentStatus documentStatus;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employeeId")
    private Employee employeeCreated;
    private Date dateCreate;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "caseId")
    private  Case _case;
}
