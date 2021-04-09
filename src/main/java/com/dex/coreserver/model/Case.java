package com.dex.coreserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="cases")
public class Case extends AbstractDataModel{
    public enum CaseStatus{
        PREPARED,
        OPEN,
        PROCESSING,
        ON_HOLD,
        DISPATCH,
        ON_VERIFICATION,
        VERIFIED,
        SUSPENDED,
        COMPLETED,
        ARCHIEVED
    }

    public enum CaseState{
        ASSIGN,
        REJECT,
        REVOKE,
        TAKEOVER
    }

    public enum CaseType{
        ADMINISTRATIVE_PROCEDURE,
        OTHER
    }

    private String caseName;
    private CaseStatus caseStatus;
    private CaseType caseType;
    private CaseState caseState;
    private String caseNumber;
    private String description;
    private Date startDate;
    private Date endDate;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name="Owner")
    private Employee owner;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name="Processor")
    private Employee processor;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name="RefersTo")
    private PhysicalEntity refersTo;
}
