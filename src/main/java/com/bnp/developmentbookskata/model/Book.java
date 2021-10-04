package com.bnp.developmentbookskata.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private String title;
    private String author;
    private Integer year;
}
