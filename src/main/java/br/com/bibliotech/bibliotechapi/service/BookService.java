package br.com.bibliotech.bibliotechapi.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bibliotech.bibliotechapi.dto.BookDTO;
import br.com.bibliotech.bibliotechapi.exceptions.ResourceNotFoundException;
import br.com.bibliotech.bibliotechapi.model.Author;
import br.com.bibliotech.bibliotechapi.model.Book;
import br.com.bibliotech.bibliotechapi.repository.AuthorRepository;
import br.com.bibliotech.bibliotechapi.repository.BookRepository;

@Service
public class BookService {
  
  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private AuthorRepository authorRepository;


  public Book createBook(BookDTO bookDTO) {
    Author author = authorRepository.findById(bookDTO.getAuthorId())
      .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + bookDTO.getAuthorId()));


    Book newBook = new Book();
    newBook.setTitle(bookDTO.getTitle());
    newBook.setCategory(bookDTO.getCategory());
    newBook.setIsbn(bookDTO.getIsbn());
    newBook.setPublicationYear(bookDTO.getPublicationYear());
    newBook.setAvailableCopies(bookDTO.getAvailableCopies());
    newBook.setAuthor(author);

    return bookRepository.save(newBook);
  }

  public List<Book> findAllBooks() {
    return bookRepository.findAll();
  }

  public Optional<Book> findBookById(UUID id) {
    return bookRepository.findById(id);
  }

  public Book updateBook(UUID id, BookDTO bookDetails) {
    Book book = bookRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    
    Author author = authorRepository.findById(bookDetails.getAuthorId())
      .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + bookDetails.getAuthorId()));

    book.setTitle(bookDetails.getTitle());
    book.setCategory(bookDetails.getCategory());
    book.setIsbn(bookDetails.getIsbn());
    book.setPublicationYear(bookDetails.getPublicationYear());
    book.setAvailableCopies(bookDetails.getAvailableCopies());
    book.setAuthor(author);

    return bookRepository.save(book);
  }

  public void deleteBook(UUID id) {
    Book book = bookRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    
    bookRepository.delete(book);
  }
}
