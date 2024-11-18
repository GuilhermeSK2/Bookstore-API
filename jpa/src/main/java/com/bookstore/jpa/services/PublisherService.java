package com.bookstore.jpa.services;

import com.bookstore.jpa.clients.PublisherClient;
import com.bookstore.jpa.models.PublisherModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PublisherService {

    @Autowired
    private PublisherClient publisherClient;

    public List<PublisherModel> getAllPublishers(){
        return publisherClient.getAllPublishers();
    }

    public PublisherModel savePublisher(PublisherModel publisher) {
        return publisherClient.savePublisher(publisher);
    }

    public PublisherModel getPublisherById(UUID id){
        return publisherClient.getById(id);
    }
}
