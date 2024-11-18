package com.bookstore.jpa.exceptions;

public class AuthorNotFoundException extends RuntimeException{
    public AuthorNotFoundException() {
        super("Autor não encontrado!");
    }

    public AuthorNotFoundException(String message) {
        super(message);
    }
}
