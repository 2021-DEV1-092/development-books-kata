package com.bnp.developmentbookskata.service;

import com.bnp.developmentbookskata.model.Book;
import com.bnp.developmentbookskata.model.BookInput;
import com.bnp.developmentbookskata.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    // TODO change name of method
    public List<Book> importBooks() {
        return bookRepository.findAll();
    }

    public List<Book> createBookListFromInput(List<BookInput> bookInputList) {
        return bookInputList.stream()
                .map(this::createListFromBookInput)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public void checkAllBooksExistsInDB(List<BookInput> bookInputList) {
        createBookListFromInput(bookInputList);
        List<Book> booksInDB = importBooks();
        for (BookInput book : bookInputList) {
            if (!booksInDB.contains(book.getBook())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book: " + book.getBook() + " not present in DB");
            }
        }
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
