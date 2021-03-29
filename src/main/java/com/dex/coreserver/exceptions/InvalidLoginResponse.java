package com.dex.coreserver.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvalidLoginResponse {
    private String username;
    private String password;

    public InvalidLoginResponse() {
        this.username = "Neispravna e-mail adresa!";
        this.password = "Neispravna lozinka!";
    }
}
