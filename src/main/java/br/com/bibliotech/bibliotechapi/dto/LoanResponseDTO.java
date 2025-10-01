package br.com.bibliotech.bibliotechapi.dto;

import br.com.bibliotech.bibliotechapi.model.enums.Status;
import java.time.LocalDate;
import java.util.UUID;

public class LoanResponseDTO {
    private UUID id;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private Status status;
    private BookInLoanResponseDTO book;

    public LoanResponseDTO(UUID id, LocalDate loanDate, LocalDate dueDate, LocalDate returnDate, Status status, BookInLoanResponseDTO book) {
        this.id = id;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
        this.book = book;
    }
  
    public UUID getId() { 
      return id; 
    }
    public LocalDate getLoanDate() { 
      return loanDate; 
    }
    public LocalDate getDueDate() { 
      return dueDate; 
    }
    public LocalDate getReturnDate() { 
      return returnDate; 
    }
    public Status getStatus() { 
      return status; 
    }
    public BookInLoanResponseDTO getBook() { 
      return book; 
    }
}