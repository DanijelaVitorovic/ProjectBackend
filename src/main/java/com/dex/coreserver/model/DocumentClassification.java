package com.dex.coreserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentClassification extends AbstractDataModel {

    private String title;
    
    @NotBlank
    @UniqueCode
    private String code;

    @Size(max = 2000)
    private String description;
}
