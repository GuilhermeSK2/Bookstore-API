package com.bookstore.jpa.services;

import com.bookstore.jpa.clients.PublisherClient;
import com.bookstore.jpa.dtos.BookRecordDto;
import com.bookstore.jpa.exceptions.AuthorNotFoundException;
import com.bookstore.jpa.exceptions.BookSaveException;
import com.bookstore.jpa.exceptions.PublisherSaveException;
import com.bookstore.jpa.models.AuthorModel;
import com.bookstore.jpa.models.BookModel;
import com.bookstore.jpa.models.PublisherModel;
import com.bookstore.jpa.models.ReviewModel;
import com.bookstore.jpa.repositories.AuthorRepository;
import com.bookstore.jpa.repositories.BookRepository;
import com.bookstore.jpa.repositories.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;

    @Autowired
    private PublisherClient publisherClient;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
    }

    // Retorna todos os livros
    public List<BookModel> getAllBooks() {
        return bookRepository.findAll();
    }

    // Retorna livros com base no parâmetro
    public Page<BookModel> findBooks(
            Boolean isActive,
            String title,
            String publisher,
            String author,
            String startDate,
            String endDate,
            int page,
            int size,
            String sortField,
            String sortDirection) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        // Converte as datas diretamente
        LocalDateTime start = LocalDateTime.parse(startDate, formatter);
        LocalDateTime end = LocalDateTime.parse(endDate, formatter);

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        Pageable pageable = PageRequest.of(page, size, sort);

        // Passa as datas convertidas diretamente
        return bookRepository.findBooksByFiltersAndDate(isActive, title, publisher, author, start, end, pageable);
    }



    public void updateBookStatus(UUID id) {
        BookModel book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado com o ID: " + id));
        bookRepository.save(book);
    }

    //Busca por titulo ou id
    public List<BookModel> searchBook(String query) {
        return bookRepository.findByIdOrTitleLike(query);
    }

    //Busca por id
    public BookModel getBookById(UUID id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Livro não encontrado"));
    }

    @Transactional
    public BookModel saveBook(BookRecordDto bookRecordDto) {
        BookModel book = new BookModel();

        try {
            // Define o status como ativo (isActive = true) ao criar o livro
            book.setActive(true);
            book.setTitle(bookRecordDto.title());
        //preenche o campo editora do livro
            PublisherModel publisher = publisherRepository.findAllById(bookRecordDto.publisherId());
        book.setPublisher(publisher);
        Set<AuthorModel> authors = authorRepository.findAllById(bookRecordDto.authorIds()).stream().collect(Collectors.toSet());
        if (authors.isEmpty()) {
            throw new AuthorNotFoundException("Nenhum autor encontrado para os IDs fornecidos.");
        }
        book.setAuthors(authors);

        } catch (Exception e) {
            throw new BookSaveException("Erro ao salvar o livro: " + e.getMessage());
        }

        ReviewModel reviewModel = new ReviewModel();
        reviewModel.setComment(bookRecordDto.reviewComment());
        reviewModel.setBook(book);
        book.setReview(reviewModel);

        return bookRepository.save(book);
    }

    @Transactional
    public BookModel updateBook(UUID id, BookRecordDto bookRecordDto) {
        BookModel book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        book.setTitle(bookRecordDto.title());//Atualiza o título
        //book.setPublisher(publisherRepository.findById(bookRecordDto.publisherId()).get());//Atualiza a editora
        //book.setAuthors(authorRepository.findAllById(bookRecordDto.authorIds()).stream().collect(Collectors.toSet()));//Atualiza o autor

        ReviewModel reviewModel = book.getReview();//
        reviewModel.setComment(bookRecordDto.reviewComment());
        book.setReview(reviewModel);
        book.setActive(bookRecordDto.isActive());//Atualiza o status

        return bookRepository.save(book);
    }


    @Transactional
    public void deleteBook(UUID id){
        bookRepository.deleteById(id);
    }
}