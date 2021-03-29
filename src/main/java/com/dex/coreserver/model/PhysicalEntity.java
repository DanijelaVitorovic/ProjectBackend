package com.dex.coreserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalEntity extends AbstractDataModel{
    private String firstName;
    private String lastName;
    private String profession;
    private String middleName;
    private String identificationNumber;
    @Embedded
    private Address address;
    private String email;

}
