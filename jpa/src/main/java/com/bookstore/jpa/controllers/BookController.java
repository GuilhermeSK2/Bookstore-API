package com.bookstore.jpa.controllers;

import com.bookstore.jpa.clients.PublisherClient;
import com.bookstore.jpa.dtos.BookRecordDto;
import com.bookstore.jpa.models.BookModel;
import com.bookstore.jpa.models.PublisherModel;
import com.bookstore.jpa.services.BookService;
import com.bookstore.jpa.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private PublisherClient publisherClient;

    @Autowired
    private PublisherService publisherService;



    @GetMapping("/status")
    public List<BookModel> getBooks(@RequestParam(required = false) Boolean isActive) {
        if (isActive == null) {
            // Se isActive for null, retorna todos os livros
            return bookService.getAllBooks();
        }
        return bookService.getBooksByStatus(isActive);
    }


    //Busca por titulo ou id
    @GetMapping("/search")
    public List<BookModel> searchBooks(@RequestParam("query") String query) {
        return bookService.searchBook(query);
    }

    //Busca por id
    @GetMapping("/{id}")
    public ResponseEntity<BookModel> getBookById(@PathVariable UUID id) {
        BookModel book = bookService.getBookById(id);
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @PostMapping("/publisher/publishers")
    public ResponseEntity<BookModel> saveBook(@RequestBody BookRecordDto bookRecordDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.saveBook(bookRecordDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookModel> updateBook(@PathVariable UUID id, @RequestBody BookRecordDto bookRecordDto) {
        BookModel updatedBook = bookService.updateBook(id, bookRecordDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedBook);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable UUID id) {
        bookService.deleteBook(id);
        return ResponseEntity.status(HttpStatus.OK).body("Book deleted successfully.");
    }
}