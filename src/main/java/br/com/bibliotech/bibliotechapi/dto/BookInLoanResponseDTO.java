package br.com.bibliotech.bibliotechapi.dto;

public class BookInLoanResponseDTO {
    private String title;
    private AuthorInLoanResponseDTO author;

    public BookInLoanResponseDTO(String title, AuthorInLoanResponseDTO author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public AuthorInLoanResponseDTO getAuthor() {
        return author;
    }
}