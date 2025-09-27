package br.com.bibliotech.bibliotechapi.dto;

public class AuthorDTO {
  private String name;
  private String nationality;

  public AuthorDTO() {
  }

  public AuthorDTO(String name, String nationality) {
    this.name = name;
    this.nationality = nationality;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNationality() {
    return nationality;
  }

  public void setNationality(String nationality) {
    this.nationality = nationality;
  }
}
