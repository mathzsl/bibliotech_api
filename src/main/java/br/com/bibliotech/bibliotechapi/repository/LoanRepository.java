package br.com.bibliotech.bibliotechapi.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bibliotech.bibliotechapi.model.Loan;
import br.com.bibliotech.bibliotechapi.model.User;

@Repository
public interface LoanRepository extends JpaRepository<Loan, UUID> {

  List<Loan> findByUser(User user);
}
