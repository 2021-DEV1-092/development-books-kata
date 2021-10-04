package com.bnp.developmentbookskata.service;

import com.bnp.developmentbookskata.model.Book;
import com.bnp.developmentbookskata.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public List<Book> importBooks() {
        return bookRepository.findAll();
    }
}
