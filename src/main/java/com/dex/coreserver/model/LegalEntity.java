package com.dex.coreserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LegalEntity extends  AbstractDataModel{

    @NotBlank(message = "name ff")
    private String name;

    @Column(unique = true)
    @NotBlank(message = "pib jdjd")
    private String pib;

    private String registrationNumber;

    @Email(message = "email")
    @NotBlank
    private String email;

    private Statment statment;
}
