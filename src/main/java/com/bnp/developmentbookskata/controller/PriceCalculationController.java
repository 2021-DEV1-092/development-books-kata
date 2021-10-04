package com.bnp.developmentbookskata.controller;

import com.bnp.developmentbookskata.model.Book;
import com.bnp.developmentbookskata.model.PriceResponse;
import com.bnp.developmentbookskata.service.PriceCalculationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PriceCalculationController {

    private final PriceCalculationService priceCalculationService;


    public PriceCalculationController(PriceCalculationService priceCalculationService) {
        this.priceCalculationService = priceCalculationService;
    }

    @PostMapping(path = "/calculatePrice", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> calculatePriceOfBooks(@RequestBody List<Book> bookList){
        Double calculatedPrice = priceCalculationService.calculatePrice(bookList);
        PriceResponse priceResponse = new PriceResponse(calculatedPrice);
        return ResponseEntity.ok(priceResponse);
    }
}
