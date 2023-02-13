package com.cathaybk.coindesk.controller;

import com.cathaybk.coindesk.dto.CoinDescDto;
import com.cathaybk.coindesk.exception.CoinDescFoundException;
import com.cathaybk.coindesk.exception.CoinDescNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureRestDocs(outputDir = "target/snippets")
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
                .andExpect(status().is(201))
                .andDo(document("coinDesc/createCoinDesc",
                        requestFields(
                                fieldWithPath("code").description("幣別代號"),
                                fieldWithPath("zhtwDesc").description("幣別中文"))
                ));
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

//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .get("/coinDesc/{code}", "JPY");
        RequestBuilder requestBuilder = RestDocumentationRequestBuilders
                .get("/coinDesc/{code}", "JPY");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.zhtwDesc", equalTo("日圓")))
                .andDo(document("coinDesc/getCoinDescByCode",
                        pathParameters(
                                parameterWithName("code").description("幣別代號")),
                        responseFields(
                                fieldWithPath("code").description("幣別代號"),
                                fieldWithPath("zhtwDesc").description("幣別中文"))
                ));
    }

    @Test
    void getCoinDesc_notFound() throws Exception{
        RequestBuilder requestBuilder = RestDocumentationRequestBuilders
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

        RequestBuilder requestBuilder = RestDocumentationRequestBuilders
                .put("/coinDesc/{code}", "JPY")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andDo(document("coinDesc/updateCoinDesc",
                        pathParameters(
                                parameterWithName("code").description("幣別代號")),
                        requestFields(
                                fieldWithPath("code").description("幣別代號"),
                                fieldWithPath("zhtwDesc").description("幣別中文"))
                ));
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
        RequestBuilder requestBuilder = RestDocumentationRequestBuilders
                .delete("/coinDesc/{code}", "JPY");

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(204))
                .andDo(document("coinDesc/deleteCoinDescByCode",
                        pathParameters(
                                parameterWithName("code").description("幣別代號"))
                ));
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
                .andDo(document("bpis",
                        responseFields(
                        fieldWithPath("updatedDateTime").description("Coin Desk BPI更新日期時間"),
                        fieldWithPath("bpis[].code").description("幣別代號"),
                        fieldWithPath("bpis[].description").description("幣別英文"),
                        fieldWithPath("bpis[].zhtwDesc").description("幣別中文"),
                        fieldWithPath("bpis[].exchangeRate").description("BitCoin匯率"))
                ));
    }
}