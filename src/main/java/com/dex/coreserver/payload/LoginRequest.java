package com.dex.coreserver.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
public class LoginRequest {
    @NotBlank(message = "Korisničko ime ne može biti prazno!")
    private String username;
    @NotBlank(message = "Lozinka ne može biti prazna!")
    private String password;
}
