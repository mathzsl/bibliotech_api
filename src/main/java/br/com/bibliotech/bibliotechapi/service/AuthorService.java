package br.com.bibliotech.bibliotechapi.service;

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
}
