package com.bookstore.jpa.controllers;

import com.bookstore.jpa.dtos.AuthorDto;
import com.bookstore.jpa.models.AuthorModel;
import com.bookstore.jpa.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<AuthorModel>> getAllAuthors() {
        return ResponseEntity.status(HttpStatus.OK).body(authorService.getAllAuthors());
    }

    @PostMapping
    public ResponseEntity<AuthorModel> savePublisher(@RequestBody AuthorDto authorDto) {
        AuthorModel author = new AuthorModel();
        author.setName(authorDto.getName());
        AuthorModel savedAuthor = authorService.saveAuthor(author);
        return ResponseEntity.ok(savedAuthor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable UUID id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.status(HttpStatus.OK).body("Autor deletado com sucesso!");
    }
}
