package com.dex.coreserver.model;

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
public class CaseMovement extends AbstractDataModel {

    public enum MovementState{
        SENT,
        REJECTED,
        REVOKED,
        RECEIVED
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "_case")
    Case _case;
    Case.CaseStatus caseStatusSend;
    Case.CaseStatus caseStatusReceived;
    Date sendTime;
    Date receivedTime;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="employeeSend")
    Employee employeeSend;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="employeeOwner")
    Employee employeeOwner;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="employeeReceived")
    Employee employeeReceived;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="employeeProcessor")
    Employee employeeProcessor;
    String description;
    String rejectDescription;
    MovementState movementState;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="employeeVerificator")
    Employee employeeVerificator;
    String notAcceptingForVerificationDescription;


}
