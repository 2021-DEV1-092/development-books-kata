package com.bnp.developmentbookskata.service;

import com.bnp.developmentbookskata.config.DiscountConfig;
import com.bnp.developmentbookskata.model.Book;
import com.bnp.developmentbookskata.model.BookInput;
import com.bnp.developmentbookskata.model.PriceResponse;
import com.bnp.developmentbookskata.utility.BookTestDataHelper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bnp.developmentbookskata.utility.BookTestDataHelper.createBookInputList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


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
    @Mock
    private BookService bookService = Mockito.mock(BookService.class);


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

        service = new PriceCalculationService(discountConfig, bookService);
    }

    @Test
    public void verify_1A_ReturnsCorrectPrice() {
        List<Book> bookList = List.of(book_1);
        List<BookInput> bookInputList = createBookInputList(bookList);
        when(bookService.createBookListFromInput(bookInputList)).thenReturn(bookList);

        PriceResponse calculatedPrice = service.calculatePrice(bookInputList);

        assertThat(calculatedPrice.getFinalPrice()).isEqualTo(50);
        assertThat(calculatedPrice.getBasePrice()).isEqualTo(50);
        assertThat(calculatedPrice.getTotalDiscount()).isEqualTo(0);
        assertThat(calculatedPrice.getTotalBooks()).isEqualTo(1);

    }

    @Test
    public void verify_2A_ReturnsCorrectPrice() {
        List<Book> bookList = List.of(
                book_1,
                book_1);
        List<BookInput> bookInputList = createBookInputList(bookList);

        when(bookService.createBookListFromInput(bookInputList)).thenReturn(bookList);

        PriceResponse calculatedPrice = service.calculatePrice(bookInputList);

        assertThat(calculatedPrice.getFinalPrice()).isEqualTo(100);
        assertThat(calculatedPrice.getBasePrice()).isEqualTo(100);
        assertThat(calculatedPrice.getTotalDiscount()).isEqualTo(0);
        assertThat(calculatedPrice.getTotalBooks()).isEqualTo(2);
    }

    @Test
    public void verify_1A_1B_ReturnsCorrectPrice() {
        List<Book> bookList = List.of(
                book_1,
                book_2);
        List<BookInput> bookInputList = createBookInputList(bookList);

        when(bookService.createBookListFromInput(bookInputList)).thenReturn(bookList);

        PriceResponse calculatedPrice = service.calculatePrice(bookInputList);

        assertThat(calculatedPrice.getFinalPrice()).isEqualTo(95);
        assertThat(calculatedPrice.getBasePrice()).isEqualTo(100);
        assertThat(calculatedPrice.getTotalDiscount()).isEqualTo(5);
        assertThat(calculatedPrice.getTotalBooks()).isEqualTo(2);
    }

    @Test
    public void verify_5A_3B_ReturnsCorrectPrice() {
        List<Book> bookList = List.of(
                book_1, book_1, book_1, book_1, book_1,
                book_2, book_2, book_2);
        List<BookInput> bookInputList = createBookInputList(bookList);

        when(bookService.createBookListFromInput(bookInputList)).thenReturn(bookList);

        PriceResponse calculatedPrice = service.calculatePrice(bookInputList);

        assertThat(calculatedPrice.getFinalPrice()).isEqualTo(385);
        assertThat(calculatedPrice.getBasePrice()).isEqualTo(400);
        assertThat(calculatedPrice.getTotalDiscount()).isEqualTo(15);
        assertThat(calculatedPrice.getTotalBooks()).isEqualTo(8);
    }

    @Test
    public void verify_1A_1B_1C_ReturnsCorrectPrice() {
        List<Book> bookList = List.of(
                book_1,
                book_2,
                book_3);
        List<BookInput> bookInputList = createBookInputList(bookList);

        when(bookService.createBookListFromInput(bookInputList)).thenReturn(bookList);

        PriceResponse calculatedPrice = service.calculatePrice(bookInputList);

        assertThat(calculatedPrice.getFinalPrice()).isEqualTo(135);
        assertThat(calculatedPrice.getBasePrice()).isEqualTo(150);
        assertThat(calculatedPrice.getTotalDiscount()).isEqualTo(15);
        assertThat(calculatedPrice.getTotalBooks()).isEqualTo(3);
    }

    @Test
    public void verify_1A_3B_2C_ReturnsCorrectPrice() {
        List<Book> bookList = List.of(
                book_1,
                book_2, book_2, book_2,
                book_3, book_3);
        List<BookInput> bookInputList = createBookInputList(bookList);

        when(bookService.createBookListFromInput(bookInputList)).thenReturn(bookList);

        PriceResponse calculatedPrice = service.calculatePrice(bookInputList);

        assertThat(calculatedPrice.getFinalPrice()).isEqualTo(280);
        assertThat(calculatedPrice.getBasePrice()).isEqualTo(300);
        assertThat(calculatedPrice.getTotalDiscount()).isEqualTo(20);
        assertThat(calculatedPrice.getTotalBooks()).isEqualTo(6);
    }

    @Test
    public void verify_1A_1B_1D_ReturnsCorrectPrice() {
        List<Book> bookList = List.of(
                book_1,
                book_2,
                book_4);
        List<BookInput> bookInputList = createBookInputList(bookList);

        when(bookService.createBookListFromInput(bookInputList)).thenReturn(bookList);

        PriceResponse calculatedPrice = service.calculatePrice(bookInputList);

        assertThat(calculatedPrice.getFinalPrice()).isEqualTo(135);
        assertThat(calculatedPrice.getBasePrice()).isEqualTo(150);
        assertThat(calculatedPrice.getTotalDiscount()).isEqualTo(15);
        assertThat(calculatedPrice.getTotalBooks()).isEqualTo(3);
    }

    @Test
    public void verify_2A_2B_2C_ReturnsCorrectPrice() {
        List<Book> bookList = List.of(
                book_1, book_1,
                book_2, book_2,
                book_3, book_3);
        List<BookInput> bookInputList = createBookInputList(bookList);

        when(bookService.createBookListFromInput(bookInputList)).thenReturn(bookList);

        PriceResponse calculatedPrice = service.calculatePrice(bookInputList);

        assertThat(calculatedPrice.getFinalPrice()).isEqualTo(270);
        assertThat(calculatedPrice.getBasePrice()).isEqualTo(300);
        assertThat(calculatedPrice.getTotalDiscount()).isEqualTo(30);
        assertThat(calculatedPrice.getTotalBooks()).isEqualTo(6);
    }

    @Test
    public void verify_1A_1B_1C_1D_ReturnsCorrectPrice() {
        List<Book> bookList = List.of(
                book_1,
                book_2,
                book_3,
                book_4);
        List<BookInput> bookInputList = createBookInputList(bookList);

        when(bookService.createBookListFromInput(bookInputList)).thenReturn(bookList);

        PriceResponse calculatedPrice = service.calculatePrice(bookInputList);

        assertThat(calculatedPrice.getFinalPrice()).isEqualTo(160);
        assertThat(calculatedPrice.getBasePrice()).isEqualTo(200);
        assertThat(calculatedPrice.getTotalDiscount()).isEqualTo(40);
        assertThat(calculatedPrice.getTotalBooks()).isEqualTo(4);
    }

    @Test
    public void verify_3A_3B_3C_2D_ReturnsCorrectPrice() {
        List<Book> bookList = List.of(
                book_1, book_1, book_1,
                book_2, book_2, book_2,
                book_3, book_3, book_3,
                book_4, book_4);
        List<BookInput> bookInputList = createBookInputList(bookList);

        when(bookService.createBookListFromInput(bookInputList)).thenReturn(bookList);

        PriceResponse calculatedPrice = service.calculatePrice(bookInputList);

        assertThat(calculatedPrice.getFinalPrice()).isEqualTo(455);
        assertThat(calculatedPrice.getBasePrice()).isEqualTo(550);
        assertThat(calculatedPrice.getTotalDiscount()).isEqualTo(95);
        assertThat(calculatedPrice.getTotalBooks()).isEqualTo(11);
    }

    @Test
    public void verify_1A_1B_1C_1D_1E_ReturnsCorrectPrice() {
        List<Book> bookList = List.of(
                book_1,
                book_2,
                book_3,
                book_4,
                book_5);
        List<BookInput> bookInputList = createBookInputList(bookList);

        when(bookService.createBookListFromInput(bookInputList)).thenReturn(bookList);

        PriceResponse calculatedPrice = service.calculatePrice(bookInputList);

        assertThat(calculatedPrice.getFinalPrice()).isEqualTo(187.5);
        assertThat(calculatedPrice.getBasePrice()).isEqualTo(250);
        assertThat(calculatedPrice.getTotalDiscount()).isEqualTo(62.5);
        assertThat(calculatedPrice.getTotalBooks()).isEqualTo(5);
    }

    @Test
    public void verify_2A_2B_2C_1D_1E_ReturnsCorrectPrice() {
        List<Book> bookList = List.of(
                book_1, book_1,
                book_2, book_2,
                book_3, book_3,
                book_4,
                book_5);
        List<BookInput> bookInputList = createBookInputList(bookList);

        when(bookService.createBookListFromInput(bookInputList)).thenReturn(bookList);

        PriceResponse calculatedPrice = service.calculatePrice(bookInputList);

        assertThat(calculatedPrice.getFinalPrice()).isEqualTo(320);
        assertThat(calculatedPrice.getBasePrice()).isEqualTo(400);
        assertThat(calculatedPrice.getTotalDiscount()).isEqualTo(80);
        assertThat(calculatedPrice.getTotalBooks()).isEqualTo(8);
    }

    @Test
    public void verifyDiscountDoesNotChangeOnIncreasingOneBook() {
        List<Book> bookList = new java.util.ArrayList<>(List.of(
                book_1, book_1,
                book_2, book_2));
        List<BookInput> bookInputList = createBookInputList(bookList);

        when(bookService.createBookListFromInput(bookInputList)).thenReturn(bookList);

        PriceResponse firstCalculation = service.calculatePrice(bookInputList);

        assertThat(firstCalculation.getFinalPrice()).isEqualTo(190);
        assertThat(firstCalculation.getBasePrice()).isEqualTo(200);
        assertThat(firstCalculation.getTotalDiscount()).isEqualTo(10);
        assertThat(firstCalculation.getTotalBooks()).isEqualTo(4);

        // Add three times the same book -> totalDiscount should be the same.
        bookList.addAll(List.of(book_1, book_1, book_1));

        PriceResponse secondCalculation = service.calculatePrice(bookInputList);

        assertThat(secondCalculation.getFinalPrice()).isEqualTo(340);
        assertThat(secondCalculation.getBasePrice()).isEqualTo(350);
        assertThat(secondCalculation.getTotalDiscount()).isEqualTo(firstCalculation.getTotalDiscount());
        assertThat(secondCalculation.getTotalBooks()).isEqualTo(7);

    }
}

