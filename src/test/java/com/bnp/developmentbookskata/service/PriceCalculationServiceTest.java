package com.bnp.developmentbookskata.service;

import com.bnp.developmentbookskata.model.Book;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PriceCalculationServiceTest {

    private Book book_1;
    private Book book_2;

    @Before
    public void setUp(){
        book_1 = Book.builder()
                .title("Clean Code")
                .author("Robert Martin")
                .year(2008).build();

        book_2 =  Book.builder()
                .title("The Clean Coder")
                .author("Robert Martin")
                .year(2011).build();
    }

    @Test
    public void verifyOneBookReturnsCorrectPrice() {
        PriceCalculationService service = new PriceCalculationService();
        List<Book> bookList = List.of(book_1);
        Double calculatedPrice = service.calculatePrice(bookList);
        assertThat(calculatedPrice).isEqualTo(50);
    }

    @Test
    public void verifyTwoSameBooksReturnsCorrectPrice() {
        PriceCalculationService service = new PriceCalculationService();
        List<Book> bookList = List.of(book_1, book_1);
        Double calculatedPrice = service.calculatePrice(bookList);
        assertThat(calculatedPrice).isEqualTo(100);
    }

    @Test
    public void verifyTwoDiffBooksReturnsCorrectPrice() {
        PriceCalculationService service = new PriceCalculationService();
        List<Book> bookList = List.of(book_1, book_2);
        Double calculatedPrice = service.calculatePrice(bookList);
        assertThat(calculatedPrice).isEqualTo(95);
    }

    @Test
    public void verifyFiveSameBooksThreeDiffBooksReturnsCorrectPrice() {
        PriceCalculationService service = new PriceCalculationService();
        List<Book> bookList = List.of(book_1, book_1, book_1, book_1, book_1, book_2, book_2, book_2);
        Double calculatedPrice = service.calculatePrice(bookList);
        assertThat(calculatedPrice).isEqualTo(385);
    }
}

