package com.bookstore.jpa.controllers;

import com.bookstore.jpa.clients.PublisherClient;
import com.bookstore.jpa.dtos.BookRecordDto;
import com.bookstore.jpa.models.PublisherModel;
import com.bookstore.jpa.services.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/publishers")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @GetMapping
    public ResponseEntity<List<PublisherModel>> getAllPublishers(){
        return ResponseEntity.status(HttpStatus.OK).body(publisherService.getAllPublishers());
    }

    @GetMapping("/{id}")
    public PublisherModel getById(@PathVariable UUID id){
        return publisherService.getPublisherById(id);
    }
}
