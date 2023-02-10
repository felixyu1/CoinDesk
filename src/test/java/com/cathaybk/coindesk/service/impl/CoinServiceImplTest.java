package com.cathaybk.coindesk.service.impl;

import com.cathaybk.coindesk.model.BpisPojo;
import com.cathaybk.coindesk.service.CoinService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CoinServiceImplTest {

    @Autowired
    private CoinService coinService;

    @Test
    void getBpisFromCoinDesk() {
        BpisPojo bpisPojo = assertDoesNotThrow(()->{
            BpisPojo bpis = coinService.getBpisFromCoinDesk();
            return bpis;
        });

        assertNotNull(bpisPojo);
    }
}