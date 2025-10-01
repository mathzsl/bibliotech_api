package br.com.bibliotech.bibliotechapi.dto;

public class UserResponseDTO {
    private String email;
    private String name;

    public UserResponseDTO(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}