package com.bnp.developmentbookskata.service;

import com.bnp.developmentbookskata.model.Book;
import com.bnp.developmentbookskata.repository.BookRepository;
import com.bnp.developmentbookskata.utility.BookTestDataHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @Mock
    BookRepository bookRepository;

    @Test
    public void verifyImportBooksReturnsExpectedBooks() {
        when(bookRepository.findAll()).thenReturn(BookTestDataHelper.returnBasicBookList());
        BookService bookService = new BookService(bookRepository);
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