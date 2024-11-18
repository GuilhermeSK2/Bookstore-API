package com.bookstore.jpa.clients;


import com.bookstore.jpa.models.PublisherModel;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "Publisher", url = "http://localhost:8081/publisher/publishers")
public interface PublisherClient {
    @GetMapping
    List<PublisherModel> getAllPublishers();

    @GetMapping("/{id}")
    PublisherModel getById(@PathVariable("id") UUID id);

    @PostMapping
    PublisherModel savePublisher(@RequestBody PublisherModel publisher);
}
