package com.bookstore.jpa.repositories;

import com.bookstore.jpa.models.AuthorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<AuthorModel, UUID> {

    @Query("SELECT a FROM AuthorModel a WHERE a.id = :id OR a.name LIKE %:name%")
    List<AuthorModel> findAuthorByIdOrName(@Param("id") UUID id, @Param("name") String name);
}
