package com.bnp.developmentbookskata.controller;


import com.bnp.developmentbookskata.DevelopmentBooksKataApplication;
import com.bnp.developmentbookskata.model.Book;
import com.bnp.developmentbookskata.model.BookInput;
import com.bnp.developmentbookskata.model.PriceResponse;
import com.bnp.developmentbookskata.utility.BookTestDataHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = DevelopmentBooksKataApplication.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class PriceCalculationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void verifyPriceCalculationForOneBook() throws Exception {
        String content = """
                [
                    {
                        "book":{
                        "title": "Clean Code",
                        "author": "Robert Martin",
                        "year": 2008
                        },
                         "count": 1
                    }
                ]""";

        PriceResponse expectedResponse =PriceResponse.builder()
                .totalBooks(1)
                .basePrice(50d)
                .totalDiscount(0d)
                .finalPrice(50d)
                .build();


        String response = mockMvc.perform(post("/calculatePrice")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PriceResponse priceResponse = new ObjectMapper().readValue(response, PriceResponse.class);

        assertThat(priceResponse).isEqualTo(expectedResponse);
    }

    @Test
    public void verifyPriceCalculationForSixSameBooks() throws Exception {
        List<Book> baseList = BookTestDataHelper.returnBasicBookList();
        List<Book> bookList = List.of(baseList.get(3), baseList.get(3), baseList.get(3), baseList.get(3), baseList.get(3), baseList.get(3));
        List<BookInput> inputList = BookTestDataHelper.createBookInputList(bookList);
        String content = objectMapper.writeValueAsString(inputList);
        String expectedResponse = "{\"finalPrice\":300.0,\"basePrice\":300.0,\"totalDiscount\":0.0,\"totalBooks\":6}";

        String response = mockMvc.perform(post("/calculatePrice")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).isEqualTo(expectedResponse);
    }


    @Test
    public void verifyPriceCalculationFor5DifferentBooks() throws Exception {
        List<BookInput> inputList = BookTestDataHelper.createBookInputList(BookTestDataHelper.returnBasicBookList());
        String content = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(inputList);
        String expectedResponse = "{\"finalPrice\":187.5,\"basePrice\":250.0,\"totalDiscount\":62.5,\"totalBooks\":5}";

        String response = mockMvc.perform(post("/calculatePrice")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(document("calculate-price-basic", preprocessResponse(prettyPrint()), responseFields(
                        fieldWithPath("finalPrice").description("The calculated final price with discounts included of the books"),
                        fieldWithPath("basePrice").description("The base price of the books without any discounts"),
                        fieldWithPath("totalDiscount").description("The total discount of the books"),
                        fieldWithPath("totalBooks").description("The total amount of books")
                )))
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).isEqualTo(expectedResponse);
    }


    @Test
    public void verifyPriceCalculationForMixOfDifferentBooks() throws Exception {
        String content = """
                [
                    {
                        "book": {
                            "title": "Clean Code",
                            "author": "Robert Martin",
                            "year": 2008
                        },
                        "count": null
                    },
                    {
                        "book": {
                            "title": "The Clean Coder",
                            "author": "Robert Martin",
                            "year": 2011
                        },
                        "count": 1
                    },
                    {
                        "book": {
                            "title": "Clean Architecture",
                            "author": "Robert Martin",
                            "year": 2017
                        }
                    },
                    {
                        "book": {
                            "title": "Test Driven Development by Example",
                            "author": "Kent Beck",
                            "year": 2003
                        },
                        "count": 1
                    },
                    {
                        "book": {
                            "title": "Working Effectively With Legacy Code",
                            "author": "Michael C. Feathers",
                            "year": 2001
                        },
                        "count": 1
                    },
                    {
                        "book": {
                            "title": "Clean Code",
                            "author": "Robert Martin",
                            "year": 2008
                        },
                        "count": null
                    },
                    {
                        "book": {
                            "title": "Clean Code",
                            "author": "Robert Martin",
                            "year": 2008
                        },
                        "count": 4
                    },
                    {
                        "book": {
                            "title": "Test Driven Development by Example",
                            "author": "Kent Beck",
                            "year": 2003
                        },
                        "count": 5
                    },
                    {
                        "book": {
                            "title": "Working Effectively With Legacy Code",
                            "author": "Michael C. Feathers",
                            "year": 2001
                        },
                        "count": 3
                    }
                ]""";

        String expectedResponse = "{\"finalPrice\":782.5,\"basePrice\":900.0,\"totalDiscount\":117.5,\"totalBooks\":18}";

        String response = mockMvc.perform(post("/calculatePrice")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(document("calculate-price-mixed", preprocessResponse(prettyPrint()), responseFields(
                        fieldWithPath("finalPrice").description("The calculated final price with discounts included of the books"),
                        fieldWithPath("basePrice").description("The base price of the books without any discounts"),
                        fieldWithPath("totalDiscount").description("The total discount of the books"),
                        fieldWithPath("totalBooks").description("The total amount of books")
                )))
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    public void verifyBadRequestExceptionForEmptyBook() throws Exception {
        Book emptyBook = Book.builder().build();
        List<BookInput> inputList = List.of(new BookInput(emptyBook, null));
        String content = objectMapper.writeValueAsString(inputList);
        String expectedResponse = "400 BAD_REQUEST \"Book: Book(id=null, title=null, author=null, year=null) not present in DB\"";

        String response = mockMvc.perform(post("/calculatePrice")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    public void verifyBadRequestExceptionForNoneExistingBook() throws Exception {
        Book noneExistingBook = Book.builder().author("Frank Herbert").title("Dune").year(1965).build();
        List<BookInput> inputList = List.of(new BookInput(noneExistingBook, null));
        String content = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(inputList);
        String expectedResponse = "400 BAD_REQUEST \"Book: Book(id=null, title=Dune, author=Frank Herbert, year=1965) not present in DB\"";

        String response = mockMvc.perform(post("/calculatePrice")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("calculate-price-wrong-book-exception", preprocessResponse(prettyPrint()), responseBody()))
                .andReturn()
                .getResponse()
                .getContentAsString();


        assertThat(response).isEqualTo(expectedResponse);
    }

    @Test
    public void verifyEmptyListReturnsCorrectResponse() throws Exception {
        List<BookInput> inputList = Collections.emptyList();
        String content = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(inputList);
        String expectedResponse = "{\"finalPrice\":0.0,\"basePrice\":0.0,\"totalDiscount\":0.0,\"totalBooks\":0}";
        String response = mockMvc.perform(post("/calculatePrice")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("calculate-price-empty-request", preprocessResponse(prettyPrint()), responseBody()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).isEqualTo(expectedResponse);
    }
}
