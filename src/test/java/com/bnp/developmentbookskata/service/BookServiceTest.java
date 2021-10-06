package com.bnp.developmentbookskata.service;

import com.bnp.developmentbookskata.model.Book;
import com.bnp.developmentbookskata.model.BookInput;
import com.bnp.developmentbookskata.repository.BookRepository;
import com.bnp.developmentbookskata.utility.BookTestDataHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.bnp.developmentbookskata.utility.BookTestDataHelper.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;
    private BookService bookService;

    @Before
    public void setUp() {
        when(bookRepository.findAll()).thenReturn(BookTestDataHelper.returnBasicBookList());
        bookService = new BookService(bookRepository);
    }

    @Test
    public void verifyImportBooksReturnsExpectedBooks() {
        List<Book> importedBooks = bookService.importBooks();
        assertThat(importedBooks).containsExactly(BOOK_1, BOOK_2, BOOK_3, BOOK_4, BOOK_5);
        assertThat(importedBooks).hasSize(5);
    }

    @Test
    public void verifyBookInputWithSingleBookCorrectlyReturnsList() {
        BookInput bookInput = new BookInput();
        bookInput.setBook(BOOK_1);
        List<BookInput> bookInputs = List.of(bookInput);
        List<Book> result = bookService.createBookListFromInput(bookInputs);
        assertThat(result).isEqualTo(List.of(BOOK_1));
    }

    @Test
    public void verifyBookInputWithDoubleBookCorrectlyReturnsList() {
        BookInput bookInput = new BookInput();
        bookInput.setBook(BOOK_1);
        bookInput.setCount(2);
        List<BookInput> bookInputs = List.of(bookInput);
        List<Book> result = bookService.createBookListFromInput(bookInputs);
        assertThat(result).isEqualTo(List.of(BOOK_1, BOOK_1));
    }


    @Test
    public void verifyBookInputWithMultipleBooksCorrectlyReturnsList() {
        BookInput bookInput_1 = new BookInput();
        BookInput bookInput_2 = new BookInput();
        BookInput bookInput_3 = new BookInput();
        bookInput_1.setBook(BOOK_1);
        bookInput_1.setCount(2);
        bookInput_2.setBook(BOOK_3);
        bookInput_2.setCount(4);
        bookInput_3.setBook(BOOK_4);
        // don't set count

        List<BookInput> bookInputs = List.of(bookInput_1, bookInput_2, bookInput_3);
        List<Book> result = bookService.createBookListFromInput(bookInputs);
        assertThat(result).isEqualTo(List.of(
                BOOK_1, BOOK_1,
                BOOK_3, BOOK_3, BOOK_3, BOOK_3,
                BOOK_4
        ));
    }

    @Test
    // Ensure that any combination of books, with or without count is accepted as input
    public void verifyBookInputWithMultipleBooksWithoutCountCorrectlyReturnsList() {
        BookInput bookInput_1 = new BookInput();
        BookInput bookInput_2 = new BookInput();
        BookInput bookInput_3 = new BookInput();
        BookInput bookInput_4 = new BookInput();
        BookInput bookInput_5 = new BookInput();
        BookInput bookInput_6 = new BookInput();
        bookInput_1.setBook(BOOK_1);
        bookInput_1.setCount(2);
        bookInput_2.setBook(BOOK_3);
        bookInput_2.setCount(4);
        bookInput_3.setBook(BOOK_4);
        // don't set count
        bookInput_4.setBook(BOOK_1);
        bookInput_5.setBook(BOOK_1);
        bookInput_5.setCount(3);
        bookInput_6.setBook(BOOK_3);

        List<BookInput> bookInputs = List.of(bookInput_1, bookInput_2, bookInput_3, bookInput_4, bookInput_5, bookInput_6);
        List<Book> result = bookService.createBookListFromInput(bookInputs);
        assertThat(result).isEqualTo(List.of(
                BOOK_1, BOOK_1,
                BOOK_3, BOOK_3, BOOK_3, BOOK_3,
                BOOK_4,
                BOOK_1, BOOK_1, BOOK_1, BOOK_1,
                BOOK_3
        ));
    }


    @Test
    public void verifyNoExceptionWhenInputBooksExist() {
        BookInput bookInput_1 = new BookInput();
        BookInput bookInput_2 = new BookInput();
        bookInput_1.setBook(BOOK_1);
        bookInput_1.setCount(2);
        bookInput_2.setBook(BOOK_3);
        bookInput_2.setCount(4);
        List<BookInput> bookInputs = List.of(bookInput_1, bookInput_2);

        assertThatCode(() -> bookService.checkAllBooksExistsInDB(bookInputs)).doesNotThrowAnyException();
    }

    @Test
    public void verifyThrowExceptionWhenInputBookDoesNotExist() {
        BookInput bookInput_1 = new BookInput();
        BookInput bookInput_2 = new BookInput();
        BookInput bookInput_3 = new BookInput();
        bookInput_1.setBook(BOOK_1);
        bookInput_1.setCount(2);
        bookInput_2.setBook(BOOK_3);
        bookInput_2.setCount(4);
        Book noneExistingBook = Book.builder()
                .year(2451)
                .title("Too like the lightning")
                .author("Ada Palmer").build();
        bookInput_3.setBook(noneExistingBook);
        List<BookInput> bookInputs = List.of(bookInput_1, bookInput_2, bookInput_3);

        assertThatThrownBy(() -> bookService.checkAllBooksExistsInDB(bookInputs))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessage("400 BAD_REQUEST \"Book: Book(id=null, title=Too like the lightning, author=Ada Palmer, year=2451) not present in DB\"");
    }

}