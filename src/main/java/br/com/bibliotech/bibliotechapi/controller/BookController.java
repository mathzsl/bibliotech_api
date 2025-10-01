package br.com.bibliotech.bibliotechapi.controller;

import br.com.bibliotech.bibliotechapi.dto.AuthorResponseDTO;
import br.com.bibliotech.bibliotechapi.dto.BookDTO;
import br.com.bibliotech.bibliotechapi.dto.BookResponseDTO;
import br.com.bibliotech.bibliotechapi.model.Book;
import br.com.bibliotech.bibliotechapi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@RequestBody BookDTO bookDTO) {
        Book newBook = bookService.createBook(bookDTO);
        BookResponseDTO responseDTO = convertToBookResponseDTO(newBook);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        List<Book> books = bookService.findAllBooks();
        List<BookResponseDTO> responseDTOs = books.stream()
                .map(this::convertToBookResponseDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable UUID id) {
        return bookService.findBookById(id)
                .map(this::convertToBookResponseDTO)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable UUID id, @RequestBody BookDTO bookDetails) {
        Book updatedBook = bookService.updateBook(id, bookDetails);
        BookResponseDTO responseDTO = convertToBookResponseDTO(updatedBook);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable UUID id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private BookResponseDTO convertToBookResponseDTO(Book book) {
        AuthorResponseDTO authorDTO = new AuthorResponseDTO(
                book.getAuthor().getId(),
                book.getAuthor().getName(),
                book.getAuthor().getNationality()
        );
        return new BookResponseDTO(
                book.getId(),
                book.getTitle(),
                book.getCategory(),
                book.getPublicationYear(),
                book.getAvailableCopies(),
                authorDTO
        );
    }
}