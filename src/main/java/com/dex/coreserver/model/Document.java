package com.dex.coreserver.model;

import com.dex.coreserver.model.enums.DocumentStatus;
import com.dex.coreserver.model.enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    @JoinColumn(name = "caseId")
    private  Case _case;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employeeCreated")
    private Employee employeeCreated;
    private Date dateCreate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "proceedingEmployee")
    private Employee proceedingEmployee;
    private Date dateProceeding;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "signingEmployee")
    private Employee signingEmployee;
    private Date signingDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "signedEmployee")
    private Employee signedEmployee;
    private Date signedDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "revokedEmployee")
    private Employee revokedEmployee;
    private Date revokedDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "verificationEmployee")
    private Employee verificationEmployee;
    private Date verificationDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "finalEmployee")
    private Employee finalEmployee;
    private Date finalDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "firstSendingEmployee")
    private Employee firstSendingEmployee;
    private Date firstSendingDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "firstReturnedEmployee")
    private Employee firstReturnedEmployee;
    private Date firstReturnedDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "secondSendingEmployee")
    private Employee secondSendingEmployee;
    private Date secondSendingDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "secondReturnedEmployee")
    private Employee secondReturnedEmployee;
    private Date secondReturnedDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "buletinBoardEmployee")
    private Employee buletinBoardEmployee;
    private Date buletinBoardDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "deliveredEmployee")
    private Employee deliveredEmployee;
    private Date deliveredDate;

    @PrePersist
    protected void onCreate(){
        this.dateCreate = new Date();
    }
}
