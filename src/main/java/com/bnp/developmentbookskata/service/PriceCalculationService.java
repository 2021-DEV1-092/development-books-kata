package com.bnp.developmentbookskata.service;

import com.bnp.developmentbookskata.model.Book;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PriceCalculationService {

    private static final double BOOK_PRICE = 50d;
    private static final double TWO_UNIQUE_BOOKS_DISCOUNT_PERCENTAGE = 5;
    private static final double THREE_UNIQUE_BOOKS_DISCOUNT_PERCENTAGE = 10;
    private static final double FOUR_UNIQUE_BOOKS_DISCOUNT_PERCENTAGE = 20;
    private static final double FIVE_UNIQUE_BOOKS_DISCOUNT_PERCENTAGE = 25;

    public Double calculatePrice(List<Book> bookList) {

        List<Set<Book>> normalSets = groupBooksInUniqueSets(bookList);
        List<Set<Book>> smallestSets = groupBooksInUniqueAndSmallestSets(bookList);

        double totalPriceNormalSets = getTotalPriceForBookSets(normalSets);
        double totalPriceSmallestSets = getTotalPriceForBookSets(smallestSets);

        return Math.min(totalPriceNormalSets, totalPriceSmallestSets);
    }

    private double getTotalPriceForBookSets(List<Set<Book>> bookListSets) {
        double totalPrice = 0d;
        for (Set<Book> set : bookListSets) {
            if (set.size() == 1) {
                totalPrice += set.size() * BOOK_PRICE;
            }
            if (set.size() == 2) {
                totalPrice = calculatePriceForBooksSet(totalPrice, set, TWO_UNIQUE_BOOKS_DISCOUNT_PERCENTAGE);
            }
            if (set.size() == 3) {
                totalPrice = calculatePriceForBooksSet(totalPrice, set, THREE_UNIQUE_BOOKS_DISCOUNT_PERCENTAGE);
            }
            if (set.size() == 4) {
                totalPrice = calculatePriceForBooksSet(totalPrice, set, FOUR_UNIQUE_BOOKS_DISCOUNT_PERCENTAGE);
            }
            if (set.size() == 5) {
                totalPrice = calculatePriceForBooksSet(totalPrice, set, FIVE_UNIQUE_BOOKS_DISCOUNT_PERCENTAGE);
            }
        }
        return totalPrice;
    }

    private List<Set<Book>> groupBooksInUniqueSets(List<Book> bookList) {
        List<Set<Book>> bookListSets = initializeBookListSets();

        for (Book book : bookList) {
            boolean bookAddedToExistingSet = addBookToExistingSet(bookListSets, book);
            if (!bookAddedToExistingSet) {
                addBookToNewSet(bookListSets, book);
            }
        }
        return bookListSets;
    }

    private List<Set<Book>> groupBooksInUniqueAndSmallestSets(List<Book> bookList) {
        List<Set<Book>> bookListSets = initializeBookListSets();

        for (Book book : bookList) {
            Integer smallestSizedSetIndex = getSmallestSizedSetIndex(bookList, bookListSets, book);

            if (smallestSizedSetIndex != null) {
                bookListSets.get(smallestSizedSetIndex).add(book);
            } else {
                addBookToNewSet(bookListSets, book);
            }
        }
        return bookListSets;
    }

    private double calculatePriceForBooksSet(double totalPrice, Set<Book> set, double threeUniqueBooksDiscountPercentage) {
        double basePrice = set.size() * BOOK_PRICE;
        double discountedPriceForSet = basePrice - ((basePrice * threeUniqueBooksDiscountPercentage) / 100.0);
        totalPrice += discountedPriceForSet;
        return totalPrice;
    }

    private boolean addBookToExistingSet(List<Set<Book>> bookListSets, Book book) {
        boolean bookAddedToExistingSet = false;
        for (Set<Book> set : bookListSets) {
            if (!set.contains(book)) {
                set.add(book);
                bookAddedToExistingSet = true;
                break;
            }
        }
        return bookAddedToExistingSet;
    }

    private Integer getSmallestSizedSetIndex(List<Book> bookList, List<Set<Book>> bookListSets, Book book) {
        Integer smallestSizedSetIndex = null;
        // Maximum size of a set is the size of the bookList
        int setSizeOfSmallestSet = bookList.size();
        for (int i = 0; i < bookListSets.size(); i++) {
            if (!bookListSets.get(i).contains(book)) {
                int setSize = bookListSets.get(i).size();
                if (setSize < setSizeOfSmallestSet) {
                    setSizeOfSmallestSet = setSize;
                    smallestSizedSetIndex = i;
                }
            }
        }
        return smallestSizedSetIndex;
    }

    private void addBookToNewSet(List<Set<Book>> bookListSets, Book book) {
        Set<Book> newSet = new HashSet<>();
        newSet.add(book);
        bookListSets.add(newSet);
    }

    private List<Set<Book>> initializeBookListSets() {
        List<Set<Book>> bookListSets = new ArrayList<>();
        Set<Book> initialSet = new HashSet<>();
        bookListSets.add(initialSet);
        return bookListSets;
    }


}
