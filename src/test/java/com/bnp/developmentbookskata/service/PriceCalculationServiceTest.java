package com.bnp.developmentbookskata.service;

import com.bnp.developmentbookskata.model.Book;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Naming convention of tests is to indicate the Book combination set to be tested:
 * 1A_2B means there are 1 book of book_1 and 2 books of book_2 in the List of Books that is to be calculated.
 * This to simplify the naming of the tests, as they are always trying to verify the price of a combination of books.
 * See the Readme for the pricing scheme
 */
public class PriceCalculationServiceTest {

    private Book book_1;
    private Book book_2;
    private Book book_3;
    private Book book_4;

    @Before
    public void setUp() {
        book_1 = Book.builder()
                .title("Clean Code")
                .author("Robert Martin")
                .year(2008).build();

        book_2 = Book.builder()
                .title("The Clean Coder")
                .author("Robert Martin")
                .year(2011).build();

        book_3 = Book.builder()
                .title("Clean Architecture")
                .author("Robert Martin")
                .year(2017).build();

        book_4 = Book.builder()
                .title("Test Driven Development by Example")
                .author("Kent Beck")
                .year(2003).build();
    }

    @Test
    public void verify_1A_ReturnsCorrectPrice() {
        PriceCalculationService service = new PriceCalculationService();
        List<Book> bookList = List.of(book_1);
        Double calculatedPrice = service.calculatePrice(bookList);

        assertThat(calculatedPrice).isEqualTo(50);
    }

    @Test
    public void verify_2A_ReturnsCorrectPrice() {
        PriceCalculationService service = new PriceCalculationService();
        List<Book> bookList = List.of(
                book_1,
                book_1);
        Double calculatedPrice = service.calculatePrice(bookList);

        assertThat(calculatedPrice).isEqualTo(100);
    }

    @Test
    public void verify_1A_1B_ReturnsCorrectPrice() {
        PriceCalculationService service = new PriceCalculationService();
        List<Book> bookList = List.of(
                book_1,
                book_2);
        Double calculatedPrice = service.calculatePrice(bookList);

        assertThat(calculatedPrice).isEqualTo(95);
    }

    @Test
    public void verify_5A_3B_ReturnsCorrectPrice() {
        PriceCalculationService service = new PriceCalculationService();
        List<Book> bookList = List.of(
                book_1, book_1, book_1, book_1, book_1,
                book_2, book_2, book_2);
        Double calculatedPrice = service.calculatePrice(bookList);

        assertThat(calculatedPrice).isEqualTo(385);
    }

    @Test
    public void verify_1A_1B_1C_ReturnsCorrectPrice() {
        PriceCalculationService service = new PriceCalculationService();
        List<Book> bookList = List.of(
                book_1,
                book_2,
                book_3);
        Double calculatedPrice = service.calculatePrice(bookList);

        assertThat(calculatedPrice).isEqualTo(135);
    }

    @Test
    public void verify_1A_3B_2C_ReturnsCorrectPrice() {
        PriceCalculationService service = new PriceCalculationService();
        List<Book> bookList = List.of(
                book_1,
                book_2, book_2, book_2,
                book_3, book_3);
        Double calculatedPrice = service.calculatePrice(bookList);
        assertThat(calculatedPrice).isEqualTo(280);
    }

    @Test
    public void verify_2A_2B_2C_ReturnsCorrectPrice() {
        PriceCalculationService service = new PriceCalculationService();
        List<Book> bookList = List.of(
                book_1, book_1,
                book_2, book_2,
                book_3, book_3);
        Double calculatedPrice = service.calculatePrice(bookList);

        assertThat(calculatedPrice).isEqualTo(270);
    }

    @Test
    public void verify_1A_1B_1C_1D_ReturnsCorrectPrice() {
        PriceCalculationService service = new PriceCalculationService();
        List<Book> bookList = List.of(
                book_1,
                book_2,
                book_3,
                book_4);
        Double calculatedPrice = service.calculatePrice(bookList);

        assertThat(calculatedPrice).isEqualTo(160);
    }

    @Test
    public void verify_3A_3B_3C_2D_ReturnsCorrectPrice() {
        PriceCalculationService service = new PriceCalculationService();
        List<Book> bookList = List.of(
                book_1, book_1, book_1,
                book_2, book_2, book_2,
                book_3, book_3, book_3,
                book_4, book_4);
        Double calculatedPrice = service.calculatePrice(bookList);

        assertThat(calculatedPrice).isEqualTo(455);
    }
}

