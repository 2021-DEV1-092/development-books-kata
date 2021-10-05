package com.bnp.developmentbookskata.utility;

import com.bnp.developmentbookskata.model.Book;
import com.bnp.developmentbookskata.model.BookInput;

import java.util.List;
import java.util.stream.Collectors;

public class BookTestDataHelper {

    public static List<Book> returnBasicBookList() {
        return List.of(BOOK_1, BOOK_2, BOOK_3, BOOK_4, BOOK_5);
    }

    public static List<BookInput> createBookInputList(List<Book> bookInputList) {
        return bookInputList.stream()
                .map(book -> new BookInput(book, 1))
                .collect(Collectors.toList());
    }

    public static Book BOOK_1 = Book.builder()
            .title("Clean Code")
            .author("Robert Martin")
            .year(2008).build();
    public static Book BOOK_2 = Book.builder()
            .title("The Clean Coder")
            .author("Robert Martin")
            .year(2011).build();
    public static Book BOOK_3 = Book.builder()
            .title("Clean Architecture")
            .author("Robert Martin")
            .year(2017).build();
    public static Book BOOK_4 = Book.builder()
            .title("Test Driven Development by Example")
            .author("Kent Beck")
            .year(2003).build();
    public static Book BOOK_5 = Book.builder()
            .title("Working Effectively With Legacy Code")
            .author("Michael C. Feathers")
            .year(2001).build();
}
