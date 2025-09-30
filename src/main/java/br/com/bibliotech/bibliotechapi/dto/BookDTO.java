package br.com.bibliotech.bibliotechapi.dto;

import java.util.UUID;

public class BookDTO {
  private String title;
  private String category;
  private String isbn;
  private int publicationYear;
  private int availableCopies;
  private UUID authorId;

  public BookDTO() {}

  public BookDTO(String title, String category, String isbn, int publicationYear, int availableCopies, UUID authorId) {
    this.title = title;
    this.category = category;
    this.isbn = isbn;
    this.publicationYear = publicationYear;
    this.availableCopies = availableCopies;
    this.authorId = authorId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public int getPublicationYear() {
    return publicationYear;
  }

  public void setPublicationYear(int publicationYear) {
    this.publicationYear = publicationYear;
  }

  public int getAvailableCopies() {
    return availableCopies;
  }

  public void setAvailableCopies(int availableCopies) {
    this.availableCopies = availableCopies;
  }

  public UUID getAuthorId() {
    return authorId;
  }

  public void setAuthorId(UUID authorId) {
    this.authorId = authorId;
  }
}
