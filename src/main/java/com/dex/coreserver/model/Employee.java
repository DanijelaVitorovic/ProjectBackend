package com.dex.coreserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Employee extends AbstractDataModel{
   private String profession;
   private boolean manager;

   @OneToOne(fetch = FetchType.EAGER)
   @JoinColumn(name="userID",nullable = false)
   @JsonIgnore
   private User user;
   //PE dodati nakon mergea

}
