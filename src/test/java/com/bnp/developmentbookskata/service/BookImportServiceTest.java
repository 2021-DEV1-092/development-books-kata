package com.bnp.developmentbookskata.service;

import com.bnp.developmentbookskata.model.Book;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BookImportServiceTest {

    @Test
    public void verifyBooksImport(){
        BookImportService bookImportService = new BookImportService();
        List<Book> importedBooks = bookImportService.importBooks();
        Book expectedBook1 = Book.builder().title("Clean Code").title("Robert Martin").year(2008).build();
        Book expectedBook2 = Book.builder().title("The Clean Coder").title("Robert Martin").year(2001).build();
        assertThat(importedBooks).containsExactly(expectedBook1, expectedBook2);
    }
}