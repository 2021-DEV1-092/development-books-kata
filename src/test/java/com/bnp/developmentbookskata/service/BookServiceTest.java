package com.bnp.developmentbookskata.service;

import com.bnp.developmentbookskata.model.Book;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BookServiceTest {

    @Test
    public void verifyImportBooksReturnsExpectedBooks() {
        BookService bookService = new BookService();
        List<Book> importedBooks = bookService.importBooks();
        Book expectedBook1 = Book.builder()
                .title("Clean Code")
                .author("Robert Martin")
                .year(2008).build();
        Book expectedBook2 = Book.builder()
                .title("The Clean Coder")
                .author("Robert Martin")
                .year(2011).build();
        Book expectedBook3 = Book.builder()
                .title("Clean Architecture")
                .author("Robert Martin")
                .year(2017).build();
        Book expectedBook4 = Book.builder()
                .title("Test Driven Development by Example")
                .author("Kent Beck")
                .year(2003).build();
        Book expectedBook5 = Book.builder()
                .title("Working Effectively With Legacy Code")
                .author("Michael C. Feathers")
                .year(2001).build();
        assertThat(importedBooks).containsExactly(expectedBook1, expectedBook2, expectedBook3, expectedBook4, expectedBook5);
        assertThat(importedBooks).hasSize(5);
    }

}