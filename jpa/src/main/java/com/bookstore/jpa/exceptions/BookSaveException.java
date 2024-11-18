package com.bookstore.jpa.exceptions;

public class BookSaveException extends RuntimeException{
    public BookSaveException() {
        super("Livro cadastrado com sucesso!");
    }

    public BookSaveException(String message) {
        super(message);
    }
}
