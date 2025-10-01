package br.com.bibliotech.bibliotechapi.service;

import br.com.bibliotech.bibliotechapi.dto.AuthorDTO;
import br.com.bibliotech.bibliotechapi.exceptions.ResourceNotFoundException;
import br.com.bibliotech.bibliotechapi.model.Author;
import br.com.bibliotech.bibliotechapi.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    public Author findAuthorById(UUID id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
    }

    public Author updateAuthor(UUID id, AuthorDTO authorDetails) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));

        author.setName(authorDetails.getName());
        author.setNationality(authorDetails.getNationality());

        return authorRepository.save(author);
    }

    public void deleteAuthor(UUID id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));

        authorRepository.delete(author);
    }
}