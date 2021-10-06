package com.bnp.developmentbookskata.service;

import com.bnp.developmentbookskata.config.DiscountConfig;
import com.bnp.developmentbookskata.model.Book;
import com.bnp.developmentbookskata.model.BookInput;
import com.bnp.developmentbookskata.model.PriceResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PriceCalculationService {

    private final DiscountConfig discountConfig;
    private final BookService bookService;

    public PriceCalculationService(DiscountConfig discountConfig, BookService bookService) {
        this.discountConfig = discountConfig;
        this.bookService = bookService;
    }

    public PriceResponse calculatePrice(List<BookInput> bookList) {
        List<Book> bookListFromInput = bookService.createBookListFromInput(bookList);
        List<Set<Book>> normalSets = groupBooksInUniqueSets(bookListFromInput);
        List<Set<Book>> smallestSets = groupBooksInUniqueAndSmallestSets(bookListFromInput);

        double totalPriceNormalSets = getTotalPriceForBookSets(normalSets);
        double totalPriceSmallestSets = getTotalPriceForBookSets(smallestSets);

        return createPriceResponse(bookListFromInput, totalPriceNormalSets, totalPriceSmallestSets);
    }

    private PriceResponse createPriceResponse(List<Book> bookListFromInput, double totalPriceNormalSets, double totalPriceSmallestSets) {
        double basePrice = bookListFromInput.size() * discountConfig.getBookPrice();
        double finalPrice = Math.min(totalPriceNormalSets, totalPriceSmallestSets);
        return PriceResponse.builder()
                .totalBooks(bookListFromInput.size())
                .basePrice(basePrice)
                .totalDiscount(basePrice - finalPrice)
                .finalPrice(finalPrice)
                .build();
    }

    private double getTotalPriceForBookSets(List<Set<Book>> bookListSets) {
        double totalPrice = 0d;
        for (Set<Book> set : bookListSets) {
            Double discountForSet = discountConfig.getDiscountMap().get(set.size());
            totalPrice += calculatePriceForBooksSet(set, discountForSet);
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

    private double calculatePriceForBooksSet(Set<Book> set, double discountForSet) {
        double calculatedPrice = 0d;
        double basePrice = set.size() * discountConfig.getBookPrice();
        double discountedPriceForSet = basePrice - ((basePrice * discountForSet) / 100.0);
        calculatedPrice += discountedPriceForSet;
        return calculatedPrice;
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
