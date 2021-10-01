package com.bnp.developmentbookskata.service;

import com.bnp.developmentbookskata.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceCalculationService {

    private static final Double BOOK_PRICE = 50d;

    public Double calculatePrice(List<Book> bookList) {
        return bookList.size() * BOOK_PRICE;
    }
}
