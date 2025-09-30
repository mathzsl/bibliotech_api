package br.com.bibliotech.bibliotechapi.controller;

import br.com.bibliotech.bibliotechapi.dto.AuthorDTO;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.bibliotech.bibliotechapi.model.Author;
import br.com.bibliotech.bibliotechapi.service.AuthorService;

@RestController
@RequestMapping("/authors")
public class AuthorController {

  @Autowired
  private AuthorService authorService;

  @PostMapping
  public ResponseEntity<Author> createAuthor(@RequestBody AuthorDTO authorDTO) {

    Author createdAuthor = authorService.createAuthor(authorDTO);

    return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<Author>> getAllAuthors() {
    List<Author> authors = authorService.findAllAuthors();
    return new ResponseEntity<>(authors, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Author> getAuthorById(@PathVariable UUID id) {
    return authorService.findAuthorById(id)
      .map(author -> new ResponseEntity<>(author, HttpStatus.OK))
      .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Author> updateAuthor(@PathVariable UUID id, @RequestBody AuthorDTO authorDetails) {
    try {
      Author updatedAuthor = authorService.updateAuthor(id, authorDetails);
      return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
    } catch (RuntimeException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteAuthor(@PathVariable UUID id) {
    try {
      authorService.deleteAuthor(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (RuntimeException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}