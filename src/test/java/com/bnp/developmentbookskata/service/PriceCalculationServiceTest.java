package com.bnp.developmentbookskata.service;

import com.bnp.developmentbookskata.config.DiscountConfig;
import com.bnp.developmentbookskata.model.Book;
import com.bnp.developmentbookskata.utility.BookTestDataHelper;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Book book_5;
    private PriceCalculationService service;

    @Before
    public void setUp() {
        List<Book> books = BookTestDataHelper.returnBasicBookList();
        Map<Integer, Double> discountMap = new HashMap<>();
        DiscountConfig discountConfig = new DiscountConfig();

        book_1 = books.get(0);
        book_2 = books.get(1);
        book_3 = books.get(2);
        book_4 = books.get(3);
        book_5 = books.get(4);

        discountConfig.setBookPrice(50d);
        discountMap.put(1, 0d);
        discountMap.put(2, 5d);
        discountMap.put(3, 10d);
        discountMap.put(4, 20d);
        discountMap.put(5, 25d);
        discountConfig.setDiscountMap(discountMap);

        service = new PriceCalculationService(discountConfig);
    }

    @Test
    public void verify_1A_ReturnsCorrectPrice() {
        List<Book> bookList = List.of(book_1);
        Double calculatedPrice = service.calculatePrice(bookList);

        assertThat(calculatedPrice).isEqualTo(50);
    }

    @Test
    public void verify_2A_ReturnsCorrectPrice() {
        List<Book> bookList = List.of(
                book_1,
                book_1);
        Double calculatedPrice = service.calculatePrice(bookList);

        assertThat(calculatedPrice).isEqualTo(100);
    }

    @Test
    public void verify_1A_1B_ReturnsCorrectPrice() {
        List<Book> bookList = List.of(
                book_1,
                book_2);
        Double calculatedPrice = service.calculatePrice(bookList);

        assertThat(calculatedPrice).isEqualTo(95);
    }

    @Test
    public void verify_5A_3B_ReturnsCorrectPrice() {
        List<Book> bookList = List.of(
                book_1, book_1, book_1, book_1, book_1,
                book_2, book_2, book_2);
        Double calculatedPrice = service.calculatePrice(bookList);

        assertThat(calculatedPrice).isEqualTo(385);
    }

    @Test
    public void verify_1A_1B_1C_ReturnsCorrectPrice() {
        List<Book> bookList = List.of(
                book_1,
                book_2,
                book_3);
        Double calculatedPrice = service.calculatePrice(bookList);

        assertThat(calculatedPrice).isEqualTo(135);
    }

    @Test
    public void verify_1A_3B_2C_ReturnsCorrectPrice() {
        List<Book> bookList = List.of(
                book_1,
                book_2, book_2, book_2,
                book_3, book_3);
        Double calculatedPrice = service.calculatePrice(bookList);
        assertThat(calculatedPrice).isEqualTo(280);
    }

    @Test
    public void verify_1A_1B_1D_ReturnsCorrectPrice() {
        List<Book> bookList = List.of(
                book_1,
                book_2,
                book_4);
        Double calculatedPrice = service.calculatePrice(bookList);

        assertThat(calculatedPrice).isEqualTo(135);
    }

    @Test
    public void verify_2A_2B_2C_ReturnsCorrectPrice() {
        List<Book> bookList = List.of(
                book_1, book_1,
                book_2, book_2,
                book_3, book_3);
        Double calculatedPrice = service.calculatePrice(bookList);

        assertThat(calculatedPrice).isEqualTo(270);
    }

    @Test
    public void verify_1A_1B_1C_1D_ReturnsCorrectPrice() {
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
        List<Book> bookList = List.of(
                book_1, book_1, book_1,
                book_2, book_2, book_2,
                book_3, book_3, book_3,
                book_4, book_4);
        Double calculatedPrice = service.calculatePrice(bookList);

        assertThat(calculatedPrice).isEqualTo(455);
    }

    @Test
    public void verify_1A_1B_1C_1D_1E_ReturnsCorrectPrice() {
        List<Book> bookList = List.of(
                book_1,
                book_2,
                book_3,
                book_4,
                book_5);
        Double calculatedPrice = service.calculatePrice(bookList);

        assertThat(calculatedPrice).isEqualTo(187.5);
    }

    @Test
    public void verify_2A_2B_2C_1D_1E_ReturnsCorrectPrice() {
        List<Book> bookList = List.of(
                book_1, book_1,
                book_2, book_2,
                book_3, book_3,
                book_4,
                book_5);
        Double calculatedPrice = service.calculatePrice(bookList);

        assertThat(calculatedPrice).isEqualTo(320);
    }


}

