package com.bnp.developmentbookskata.utility;

import com.bnp.developmentbookskata.model.Book;

import java.util.List;

public class BookTestDataHelper {

    public static List<Book> returnBasicBookList(){
        return List.of(Book.builder()
                        .title("Clean Code")
                        .author("Robert Martin")
                        .year(2008).build(),
                Book.builder()
                        .title("The Clean Coder")
                        .author("Robert Martin")
                        .year(2011).build(),
                Book.builder()
                        .title("Clean Architecture")
                        .author("Robert Martin")
                        .year(2017).build(),
                Book.builder()
                        .title("Test Driven Development by Example")
                        .author("Kent Beck")
                        .year(2003).build(),
                Book.builder()
                        .title("Working Effectively With Legacy Code")
                        .author("Michael C. Feathers")
                        .year(2001).build());
    }
}
