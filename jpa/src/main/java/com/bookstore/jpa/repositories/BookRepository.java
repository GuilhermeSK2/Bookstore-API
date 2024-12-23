package com.bookstore.jpa.repositories;

import com.bookstore.jpa.models.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<BookModel, UUID> {
    BookModel findBookModelByTitle(String title);
    BookModel findBookModelById(UUID id);

    @Query(value = "SELECT * FROM tb_book WHERE publisher_id = :id", nativeQuery = true)
    List<BookModel> findBooksByPublisherId(@Param("id") UUID id);

    @Query("SELECT b FROM BookModel b WHERE CAST(b.id AS string) LIKE %:query% OR b.title LIKE %:query%")
    List<BookModel> findByIdOrTitleLike(@Param("query") String query);

    // Metodo para buscar livros com base no status
    List<BookModel> findByIsActive(Boolean isActive);

    // Metodo para buscar todos os livros
    List<BookModel> findAll();


    @Query("SELECT b FROM BookModel b " +
            "LEFT JOIN b.authors a " +
            "WHERE (:isActive IS NULL OR b.isActive = :isActive) " +
            "AND (:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND (:publisher IS NULL OR LOWER(b.publisher.name) LIKE LOWER(CONCAT('%', :publisher, '%'))) " +
            "AND (:author IS NULL OR LOWER(a.name) LIKE LOWER(CONCAT('%', :author, '%'))) " +
            "AND ((CAST(:startDate as DATE) IS NULL) OR b.createdAt >= :startDate) " +
            "AND ((CAST(:endDate as DATE) IS NULL) OR b.createdAt <= :endDate)")
    Page<BookModel> findBooksByFiltersAndDate(
            Boolean isActive,
            String title,
            String publisher,
            String author,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);
}
