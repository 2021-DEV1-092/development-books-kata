package com.bnp.developmentbookskata.controller;

import com.bnp.developmentbookskata.DevelopmentBooksKataApplication;
import com.bnp.developmentbookskata.model.Book;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = DevelopmentBooksKataApplication.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void verifyReturnListOfExpectedBooks() throws Exception {
        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(Book.builder()
                .id(1)
                .title("Clean Code")
                .author("Robert Martin")
                .year(2008).build());
        expectedBooks.add(Book.builder()
                .id(2)
                .title("The Clean Coder")
                .author("Robert Martin")
                .year(2011).build());
        expectedBooks.add(Book.builder()
                .id(3)
                .title("Clean Architecture")
                .author("Robert Martin")
                .year(2017).build());
        expectedBooks.add(Book.builder()
                .id(4)
                .title("Test Driven Development by Example")
                .author("Kent Beck")
                .year(2003).build());
        expectedBooks.add(Book.builder()
                .id(5)
                .title("Working Effectively With Legacy Code")
                .author("Michael C. Feathers")
                .year(2001).build());

        String responseAsString = mockMvc.perform(get("/books"))
                .andExpect(jsonPath("$.[0].title").value("Clean Code"))
                .andExpect(jsonPath("$.[0].author").value("Robert Martin"))
                .andExpect(jsonPath("$.[0].year").value(2008))
                .andExpect(status().isOk())
                .andDo(print()).andReturn().getResponse().getContentAsString();

        List<Book> returnedBooks = new ObjectMapper().readValue(responseAsString, new TypeReference<>() {
        });

        assertThat(returnedBooks).isEqualTo(expectedBooks);
    }

}