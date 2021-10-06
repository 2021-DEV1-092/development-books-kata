package com.bnp.developmentbookskata.controller;

import com.bnp.developmentbookskata.model.BookInput;
import com.bnp.developmentbookskata.model.PriceResponse;
import com.bnp.developmentbookskata.service.BookService;
import com.bnp.developmentbookskata.service.PriceCalculationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class PriceCalculationController {

    private final PriceCalculationService priceCalculationService;
    private final BookService bookService;


    public PriceCalculationController(PriceCalculationService priceCalculationService, BookService bookService) {
        this.priceCalculationService = priceCalculationService;
        this.bookService = bookService;
    }

    @PostMapping(path = "/calculatePrice", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> calculatePriceOfBooks(@RequestBody List<BookInput> bookList) {
        try {
            bookService.checkAllBooksExistsInDB(bookList);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
        Double calculatedPrice = priceCalculationService.calculatePrice(bookList);
        PriceResponse priceResponse = new PriceResponse(calculatedPrice);
        return ResponseEntity.ok(priceResponse);
    }
}
