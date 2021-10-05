package com.bnp.developmentbookskata.service;

import com.bnp.developmentbookskata.model.Book;
import com.bnp.developmentbookskata.model.BookInput;
import com.bnp.developmentbookskata.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> importBooks() {
        return bookRepository.findAll();
    }

    public List<Book> createBookListFromInput(List<BookInput> bookInputList) {
        return bookInputList.stream()
                .map(this::createListFromBookInput)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<Book> createListFromBookInput(BookInput bookInput) {
        if (bookInput.getCount() == null) {
            return createBookListWithOneElement(bookInput);
        } else {
            return createBookListWithCountElements(bookInput);
        }
    }

    private List<Book> createBookListWithCountElements(BookInput bookInput) {
        List<Book> extractedList = new ArrayList<>();
        for (int i = 0; i < bookInput.getCount(); i++) {
            extractedList.add(bookInput.getBook());
        }
        return extractedList;
    }

    private List<Book> createBookListWithOneElement(BookInput bookInput) {
        List<Book> books = new ArrayList<>();
        books.add(bookInput.getBook());
        return books;
    }
}
