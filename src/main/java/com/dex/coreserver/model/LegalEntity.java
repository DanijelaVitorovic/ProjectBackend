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

    @NotBlank(message = "Ime je obavezno polje")
    private String name;

    @Column(unique = true)
    private String pib;

    private String registrationNumber;

    @Email(message = "E-mail nije unet u propisanom formatu!")
    @NotBlank
    private String email;

    private Statment statment;
}
