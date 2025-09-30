package br.com.bibliotech.bibliotechapi.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bibliotech.bibliotechapi.dto.AuthorDTO;
import br.com.bibliotech.bibliotechapi.model.Author;
import br.com.bibliotech.bibliotechapi.repository.AuthorRepository;

@Service
public class AuthorService {
  
  @Autowired
  private AuthorRepository authorRepository;

  public Author createAuthor(AuthorDTO authorDTO) {
    Author newAuthor = new Author();
    newAuthor.setName(authorDTO.getName());
    newAuthor.setNationality(authorDTO.getNationality());

    return authorRepository.save(newAuthor);
  }

  public List<Author> findAllAuthors() {
    return authorRepository.findAll();
  }

  public Optional<Author> findAuthorById(UUID id) {
    return authorRepository.findById(id);
  }

  public Author updateAuthor(UUID id, AuthorDTO authorDetails) {
    Author author = authorRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Author not found with with id: " + id));
  
    author.setName(authorDetails.getName());
    author.setNationality(authorDetails.getNationality());

    return authorRepository.save(author);
  }

  public void deleteAuthor(UUID id) {
    Author author = authorRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
    
    authorRepository.delete(author);
  }
}
