package com.dex.coreserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Role extends AbstractDataModel {
    private String roleName;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private List<User> users=new ArrayList<>();
}
