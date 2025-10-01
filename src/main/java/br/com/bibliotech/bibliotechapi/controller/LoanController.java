package br.com.bibliotech.bibliotechapi.controller;

import br.com.bibliotech.bibliotechapi.dto.LoanDTO;
import br.com.bibliotech.bibliotechapi.dto.LoanResponseDTO;
import br.com.bibliotech.bibliotechapi.model.Loan;
import br.com.bibliotech.bibliotechapi.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping
    public ResponseEntity<?> createLoan(@RequestBody LoanDTO loanDTO) {
        try {
            Loan newLoan = loanService.createLoan(loanDTO);
            return new ResponseEntity<>(newLoan, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<List<LoanResponseDTO>> getUserLoans() {
        List<LoanResponseDTO> loans = loanService.findLoansByUser();
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<LoanResponseDTO>> getAllLoans() {
        List<LoanResponseDTO> loans = loanService.findAllLoans();
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    @PatchMapping("/{id}/return")
    public ResponseEntity<?> returnBook(@PathVariable UUID id) {
        try {
            LoanResponseDTO returnedLoan = loanService.returnBook(id);
            return new ResponseEntity<>(returnedLoan, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}