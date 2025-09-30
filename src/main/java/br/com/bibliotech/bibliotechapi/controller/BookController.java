package br.com.bibliotech.bibliotechapi.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bibliotech.bibliotechapi.dto.BookDTO;
import br.com.bibliotech.bibliotechapi.model.Book;
import br.com.bibliotech.bibliotechapi.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {
  
  @Autowired
  private BookService bookService;

  @PostMapping
  public ResponseEntity<Book> createBook(@RequestBody BookDTO bookDTO) {
    try {
      Book createdBook = bookService.createBook(bookDTO);
      return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    } catch (RuntimeException e) {
      
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping
  public ResponseEntity<List<Book>> getAllBooks() {
    List<Book> books = bookService.findAllBooks();
    return new ResponseEntity<>(books, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Book> getBookById(@PathVariable UUID id) {
    return bookService.findBookById(id)
      .map(book -> new ResponseEntity<>(book, HttpStatus.OK))
      .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Book> updateBook(@PathVariable UUID id, @RequestBody BookDTO bookDetails) {
    try {
      Book updatedBook = bookService.updateBook(id, bookDetails);
      return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    } catch (RuntimeException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteBook(@PathVariable UUID id) {
    try {
      bookService.deleteBook(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (RuntimeException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
