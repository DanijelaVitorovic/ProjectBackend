package com.dex.coreserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationalUnit extends AbstractDataModel {

    private String name;
    @NotBlank
    @UniqueCode
    private String code;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "legalEntityId")
    private LegalEntity legalEntity;
}
