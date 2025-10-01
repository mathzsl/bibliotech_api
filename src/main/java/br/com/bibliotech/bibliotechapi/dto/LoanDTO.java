package br.com.bibliotech.bibliotechapi.dto;

import java.util.UUID;

public class LoanDTO {
    private UUID bookId;

    public LoanDTO() {
    }

    public LoanDTO(UUID bookId) {
        this.bookId = bookId;
    }

    public UUID getBookId() {
        return bookId;
    }

    public void setBookId(UUID bookId) {
        this.bookId = bookId;
    }
}