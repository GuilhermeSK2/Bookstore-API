package com.bookstore.jpa.controllers;

import com.bookstore.jpa.clients.PublisherClient;
import com.bookstore.jpa.dtos.BookRecordDto;
import com.bookstore.jpa.models.BookModel;
import com.bookstore.jpa.services.BookService;
import com.bookstore.jpa.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


    @GetMapping
    public ResponseEntity<Page<BookModel>> getBooks(
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String publisher,
            @RequestParam(required = false) String author,
            @RequestParam(defaultValue = "01/01/2000 00:00:00") String startDate,
            @RequestParam(defaultValue = "01/01/2200 00:00:00") String endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "ASC") String sortDirection) {


        Page<BookModel> books = bookService.findBooks(isActive, title, publisher, author, startDate, endDate, page, size, sortField, sortDirection);
        return ResponseEntity.ok(books);
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