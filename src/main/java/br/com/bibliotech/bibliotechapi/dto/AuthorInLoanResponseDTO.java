package br.com.bibliotech.bibliotechapi.dto;

public class AuthorInLoanResponseDTO {
    private String name;

    public AuthorInLoanResponseDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}