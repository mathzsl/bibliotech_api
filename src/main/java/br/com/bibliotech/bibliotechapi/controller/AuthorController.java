package br.com.bibliotech.bibliotechapi.controller;

import br.com.bibliotech.bibliotechapi.dto.AuthorDTO;

import java.util.List;

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
}