package com.dex.coreserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Process extends AbstractDataModel{

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "processTypeID")
    private ProcessType processType;

    private String nextCaseStatus;
}
