package br.com.bibliotech.bibliotechapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponseDTO {

    @JsonProperty("accessToken")
    private String token;

    private UserResponseDTO user;

    public LoginResponseDTO(String token, UserResponseDTO user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public UserResponseDTO getUser() {
        return user;
    }
}