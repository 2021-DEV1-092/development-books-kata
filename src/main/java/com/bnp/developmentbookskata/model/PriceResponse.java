package com.bnp.developmentbookskata.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceResponse {
    private Double finalPrice;
    private Double basePrice;
    private Double totalDiscount;
    private Integer totalBooks;
}
