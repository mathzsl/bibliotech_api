package br.com.bibliotech.bibliotechapi.model;

import java.util.UUID;

import br.com.bibliotech.bibliotechapi.model.enums.Status;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "loans")
public class Loan {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;
  
  private LocalDate loanDate;
  private LocalDate dueDate;
  private LocalDate returnDate;

  @Enumerated(EnumType.STRING)
  private Status status;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "book_id")
  private Book book;

  public Loan() {}

  public Loan(LocalDate loanDate, LocalDate dueDate, Status status, User user, Book book) {
    this.loanDate = loanDate;
    this.dueDate = dueDate;
    this.status = status;
    this.user = user;
    this.book = book;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public LocalDate getLoanDate() {
    return loanDate;
  }

  public void setLoanDate(LocalDate loanDate) {
    this.loanDate = loanDate;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  public LocalDate getReturnDate() {
    return returnDate;
  }

  public void setReturnDate(LocalDate returnDate) {
    this.returnDate = returnDate;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Book getBook() {
    return book;
  }

  public void setBook(Book book) {
    this.book = book;
  }
}
