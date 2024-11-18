package com.bookstore.jpa.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class AuthorDto {
    private UUID id;
    private String name;
}
