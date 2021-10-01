package com.bnp.developmentbookskata.service;

import com.bnp.developmentbookskata.model.Book;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PriceCalculationServiceTest {

    @Test
    public void canCreatePriceCalculation() {
        PriceCalculationService service = new PriceCalculationService();
        List<Book> bookList = List.of(Book.builder()
                        .title("Clean Code")
                        .author("Robert Martin")
                        .year(2008).build());
        Double calculatedPrice = service.calculatePrice(bookList);
        assertThat(calculatedPrice).isEqualTo(50);
    }
}
