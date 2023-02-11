package com.cathaybk.coindesk.controller;

import com.cathaybk.coindesk.dto.CoinDescDto;
import com.cathaybk.coindesk.exception.CoinDescFoundException;
import com.cathaybk.coindesk.exception.CoinDescNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CoinControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    @Test
    void createCoinDesc_success() throws Exception {

        CoinDescDto coinDescDto = new CoinDescDto();
        coinDescDto.setCode("USD");
        coinDescDto.setZhtwDesc("美元");

        String json = objectMapper.writeValueAsString(coinDescDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/coinDesc")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201));
    }

    @Transactional
    @Test
    void createCoinDesc_createDuplicated() throws Exception{

        CoinDescDto coinDescDto = new CoinDescDto();
        coinDescDto.setCode("JPY");
        coinDescDto.setZhtwDesc("日圓");

        String json = objectMapper.writeValueAsString(coinDescDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/coinDesc")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertTrue(result.getResolvedException()
                                instanceof CoinDescFoundException));
    }

    @Test
    void getCoinDesc_success() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/coinDesc/{code}", "JPY");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.zhtwDesc", equalTo("日圓")));
    }

    @Test
    void getCoinDesc_notFound() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/coinDesc/{code}", "XXX");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertTrue(result.getResolvedException()
                                instanceof CoinDescNotFoundException));
    }

    @Transactional
    @Test
    void updateCoinDesc_success() throws Exception {

        CoinDescDto coinDescDto = new CoinDescDto();
        coinDescDto.setCode("JPY");
        coinDescDto.setZhtwDesc("日元");

        String json = objectMapper.writeValueAsString(coinDescDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/coinDesc/{code}", "JPY")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200));
    }

    @Transactional
    @Test
    void updateCoinDesc_updateNonExisting() throws Exception {

        CoinDescDto coinDescDto = new CoinDescDto();
        coinDescDto.setCode("ZZZ");
        coinDescDto.setZhtwDesc("金圓");

        String json = objectMapper.writeValueAsString(coinDescDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/coinDesc/{code}", "ZZZ")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertTrue(result.getResolvedException()
                                instanceof CoinDescNotFoundException));
    }

    @Transactional
    @Test
    void deleteCoinDesc_success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/coinDesc/{code}", "JPY");

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(204));
    }

    @Transactional
    @Test
    void deleteCoinDesc_deleteNonExisting() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/coinDesc/{code}", "ZZZ");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertTrue(result.getResolvedException()
                                instanceof CoinDescNotFoundException));
    }

    @Test
    void getBPIs() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/bpis");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                ;
    }
}