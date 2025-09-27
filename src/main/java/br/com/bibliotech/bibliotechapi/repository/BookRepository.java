package br.com.bibliotech.bibliotechapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bibliotech.bibliotechapi.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {}