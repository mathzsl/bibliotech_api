package br.com.bibliotech.bibliotechapi.service;

import br.com.bibliotech.bibliotechapi.dto.AuthorInLoanResponseDTO;
import br.com.bibliotech.bibliotechapi.dto.BookInLoanResponseDTO;
import br.com.bibliotech.bibliotechapi.dto.LoanDTO;
import br.com.bibliotech.bibliotechapi.dto.LoanResponseDTO;
import br.com.bibliotech.bibliotechapi.model.Book;
import br.com.bibliotech.bibliotechapi.model.Loan;
import br.com.bibliotech.bibliotechapi.model.User;
import br.com.bibliotech.bibliotechapi.model.enums.Role;
import br.com.bibliotech.bibliotechapi.model.enums.Status;
import br.com.bibliotech.bibliotechapi.repository.BookRepository;
import br.com.bibliotech.bibliotechapi.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BookRepository bookRepository;

    public Loan createLoan(LoanDTO loanDTO) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Book book = bookRepository.findById(loanDTO.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + loanDTO.getBookId()));

        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("No available copies for loan.");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        Loan newLoan = new Loan();
        newLoan.setUser(user);
        newLoan.setBook(book);
        newLoan.setLoanDate(LocalDate.now());
        newLoan.setDueDate(LocalDate.now().plusWeeks(2));
        newLoan.setStatus(Status.ACTIVE);

        return loanRepository.save(newLoan);
    }

    public List<LoanResponseDTO> findLoansByUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Loan> loans = loanRepository.findByUser(user);

        return loans.stream()
                .map(this::convertToLoanResponseDTO)
                .collect(Collectors.toList());
    }

    public List<LoanResponseDTO> findAllLoans() {
        List<Loan> loans = loanRepository.findAll();
        return loans.stream()
                .map(this::convertToLoanResponseDTO)
                .collect(Collectors.toList());
    }

    public LoanResponseDTO returnBook(UUID loanId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found with id: " + loanId));

        if (user.getRole() == Role.USER && !loan.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied. You can only return your own loans.");
        }

        loan.setStatus(Status.RETURNED);
        loan.setReturnDate(LocalDate.now());

        Book book = loan.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        Loan savedLoan = loanRepository.save(loan);
        return convertToLoanResponseDTO(savedLoan);
    }

    private LoanResponseDTO convertToLoanResponseDTO(Loan loan) {
        AuthorInLoanResponseDTO authorDTO = new AuthorInLoanResponseDTO(loan.getBook().getAuthor().getName());
        BookInLoanResponseDTO bookDTO = new BookInLoanResponseDTO(loan.getBook().getTitle(), authorDTO);
        return new LoanResponseDTO(
                loan.getId(),
                loan.getLoanDate(),
                loan.getDueDate(),
                loan.getReturnDate(),
                loan.getStatus(),
                bookDTO
        );
    }
}