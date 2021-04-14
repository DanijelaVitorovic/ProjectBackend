package com.dex.coreserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CaseClassification extends AbstractDataModel {

    private String code;
    private String name;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="OrganizationalUnit")
    private OrganizationalUnit organizationalUnit;
}
