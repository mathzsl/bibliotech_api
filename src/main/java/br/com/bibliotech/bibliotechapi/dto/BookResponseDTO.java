package br.com.bibliotech.bibliotechapi.dto;

import java.util.UUID;

public class BookResponseDTO {
    private UUID id;
    private String title;
    private String category;
    private int publicationYear;
    private int availableCopies;
    private AuthorResponseDTO author; 

    // Construtor
    public BookResponseDTO(UUID id, String title, String category, int publicationYear, int availableCopies, AuthorResponseDTO author) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.publicationYear = publicationYear;
        this.availableCopies = availableCopies;
        this.author = author;
    }
    
    public UUID getId() { 
      return id; 
    }
    public String getTitle() { 
      return title; 
    }
    public String getCategory() { 
      return category; 
    }
    public int getPublicationYear() { 
      return publicationYear; 
    }
    public int getAvailableCopies() { 
      return availableCopies; 
    }
    public AuthorResponseDTO getAuthor() { 
      return author; 
    }
}