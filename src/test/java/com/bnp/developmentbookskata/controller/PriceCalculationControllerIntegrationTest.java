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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = DevelopmentBooksKataApplication.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PriceCalculationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void verifyPriceCalculationForOneBook() throws Exception {
        List<Book> inputList = List.of(BookTestDataHelper.returnBasicBookList().get(0));
        String content = objectMapper.writeValueAsString(inputList);
        PriceResponse expectedResponse = new PriceResponse();
        expectedResponse.setTotalPrice(50d);

        String response = mockMvc.perform(post("/calculatePrice")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PriceResponse priceReponse = new ObjectMapper().readValue(response, PriceResponse.class);

        assertThat(priceReponse).isEqualTo(expectedResponse);
    }

    @Test
    public void verifyPriceCalculationForSixSameBooks() throws Exception {
        List<Book> baseList = BookTestDataHelper.returnBasicBookList();
        List<Book> inputList = List.of(baseList.get(3), baseList.get(3), baseList.get(3), baseList.get(3), baseList.get(3), baseList.get(3));
        String content = objectMapper.writeValueAsString(inputList);
        PriceResponse expectedResponse = new PriceResponse();
        expectedResponse.setTotalPrice(300d);

        String response = mockMvc.perform(post("/calculatePrice")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PriceResponse priceReponse = new ObjectMapper().readValue(response, PriceResponse.class);

        assertThat(priceReponse).isEqualTo(expectedResponse);
    }


    @Test
    public void verifyPriceCalculationFor5DifferentBooks() throws Exception {
        List<BookInput> inputList = BookTestDataHelper.createBookInputList(BookTestDataHelper.returnBasicBookList());
        String content = objectMapper.writeValueAsString(inputList);
        PriceResponse expectedResponse = new PriceResponse();
        expectedResponse.setTotalPrice(187.5d);

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
}
