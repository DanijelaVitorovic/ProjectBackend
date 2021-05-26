package com.dex.coreserver.model;

import com.dex.coreserver.model.enums.DocumentMovementStatement;
import com.dex.coreserver.model.enums.DocumentStatus;
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
public class DocumentMovement extends AbstractDataModel{

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "document")
    private Document document;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="employeeSend")
    Employee employeeSend;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="employeeReceived")
    Employee employeeReceived;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employeeCreated")
    private Employee employeeCreated;
    private Date dateProceeding;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "verificationEmployee")
    private Employee verificationEmployee;
    private Date verificationDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "signingEmployee")
    private Employee signingEmployee;
    private Date signingDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "signedEmployee")
    private Employee signedEmployee;
    private Date signedDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "finalEmployee")
    private Employee finalEmployee;
    private Date finalDate;

    private DocumentMovementStatement documentMovementStatement;
    private String description;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="employeeVerificator")
    Employee employeeVerificator;

}
