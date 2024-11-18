package com.bookstore.jpa.services;

import com.bookstore.jpa.models.AuthorModel;

import com.bookstore.jpa.models.PublisherModel;
import com.bookstore.jpa.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<AuthorModel> getAllAuthors(){
        return authorRepository.findAll();
    }

    public AuthorModel saveAuthor(AuthorModel author) {
        return authorRepository.save(author);
    }

    public void deleteAuthor(UUID id){
        authorRepository.deleteById(id);
    }
}