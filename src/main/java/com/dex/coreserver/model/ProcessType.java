package com.dex.coreserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessType extends AbstractDataModel{

    @NotBlank(message = "{notblank.processType}")
    private String type;
    @Column
    @Size(max = 2000)
    private String description;
}
