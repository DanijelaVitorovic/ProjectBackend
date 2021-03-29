package com.dex.coreserver.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name="users",
        uniqueConstraints=
        @UniqueConstraint(columnNames={"username"})
)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @JsonFormat(pattern = "dd.MM.yyyy")
    @Column(updatable = false)
    private Date createdAt;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date updatedAt;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private Date lastLoginDate;

    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }

    @NotBlank(message = "Morate uneti ime!")
    private String firstName;
    @NotBlank(message = "Morate uneti prezime!")
    private String lastName;
    @Email(message = "E-mail nije unet u propisanom formatu!")
    @NotBlank(message = "Morate uneti e-mail adresu!")
    private String username;
    @NotBlank(message = "Morate uneti lozinku!")
    private String password;
    private String active;
    @Transient
    private String confirmPassword;
    private String address;
    private String phoneNumber;
    private boolean isDeleted;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    private List<Role> roles=new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        if(this.getActive().equals("DA"))
            return true;
        else
            return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
