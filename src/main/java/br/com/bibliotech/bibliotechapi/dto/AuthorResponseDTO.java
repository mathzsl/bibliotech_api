package br.com.bibliotech.bibliotechapi.dto;

import java.util.UUID;

public class AuthorResponseDTO {
    private UUID id;
    private String name;
    private String nationality;

    public AuthorResponseDTO(UUID id, String name, String nationality) {
        this.id = id;
        this.name = name;
        this.nationality = nationality;
    }

    public UUID getId() { 
      return id; 
    }
    public String getName() { 
      return name; 
    }
    public String getNationality() { return 
      nationality; 
    }
}