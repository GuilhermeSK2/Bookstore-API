package com.bookstore.jpa.exceptions;

public class PublisherSaveException extends RuntimeException{
    public PublisherSaveException(){
        super("Editora cadastrada como sucesso!");
    }

    public PublisherSaveException(String message) {
        super(message);
    }
}
