package br.com.bibliotech.bibliotechapi.controller;

import br.com.bibliotech.bibliotechapi.dto.AuthorDTO;
import br.com.bibliotech.bibliotechapi.dto.AuthorResponseDTO;
import br.com.bibliotech.bibliotechapi.model.Author;
import br.com.bibliotech.bibliotechapi.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping
    public ResponseEntity<AuthorResponseDTO> createAuthor(@RequestBody AuthorDTO authorDTO) {
        Author createdAuthor = authorService.createAuthor(authorDTO);
        AuthorResponseDTO responseDTO = new AuthorResponseDTO(createdAuthor.getId(), createdAuthor.getName(), createdAuthor.getNationality());
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponseDTO>> getAllAuthors() {
        List<Author> authors = authorService.findAllAuthors();
        List<AuthorResponseDTO> responseDTOs = authors.stream()
                .map(author -> new AuthorResponseDTO(author.getId(), author.getName(), author.getNationality()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDTO> getAuthorById(@PathVariable UUID id) {
        Author author = authorService.findAuthorById(id);
        AuthorResponseDTO responseDTO = new AuthorResponseDTO(author.getId(), author.getName(), author.getNationality());
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponseDTO> updateAuthor(@PathVariable UUID id, @RequestBody AuthorDTO authorDetails) {
        Author updatedAuthor = authorService.updateAuthor(id, authorDetails);
        AuthorResponseDTO responseDTO = new AuthorResponseDTO(updatedAuthor.getId(), updatedAuthor.getName(), updatedAuthor.getNationality());
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAuthor(@PathVariable UUID id) {
        authorService.deleteAuthor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}