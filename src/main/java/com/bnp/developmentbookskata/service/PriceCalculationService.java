package com.bnp.developmentbookskata.service;

import com.bnp.developmentbookskata.model.Book;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PriceCalculationService {

    private static final double BOOK_PRICE = 50d;
    private static final double TWO_BOOK_DISCOUNT_PERCENTAGE = 5;

    public Double calculatePrice(List<Book> bookList) {
        List<Set<Book>> bookListSets = new ArrayList<>();
        Set<Book> initialSet = new HashSet<>();

        bookListSets.add(initialSet);

        groupBooksInUniqueSets(bookList, bookListSets);

        double totalPrice = 0d;
        for (Set<Book> set : bookListSets) {
            if (set.size() == 1) {
                totalPrice += set.size() * BOOK_PRICE;
            }
            if (set.size() == 2) {
                double basePrice = set.size() * BOOK_PRICE;
                double discountedPriceForSet = basePrice - ((basePrice * TWO_BOOK_DISCOUNT_PERCENTAGE) / 100.0);
                totalPrice += discountedPriceForSet;
            }
        }

        return totalPrice;
    }

    private void groupBooksInUniqueSets(List<Book> bookList, List<Set<Book>> bookListSets) {
        for (Book book : bookList) {
            boolean bookAddedToExistingSet = false;
            for (Set<Book> set : bookListSets) {
                if (!set.contains(book)) {
                    set.add(book);
                    bookAddedToExistingSet = true;
                    break;
                }
            }
            if (!bookAddedToExistingSet) {
                Set<Book> newSet = new HashSet<>();
                newSet.add(book);
                bookListSets.add(newSet);
            }
        }
    }

}
