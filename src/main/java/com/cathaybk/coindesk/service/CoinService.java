package com.cathaybk.coindesk.service;

import com.cathaybk.coindesk.model.BpisPojo;
import com.cathaybk.coindesk.model.CoinDesc;

import java.util.List;

public interface CoinService {

    public void createCoinDesc(CoinDesc coinDesc) throws RuntimeException;
    public void updateCoinDesc(String code, CoinDesc coinDesc) throws RuntimeException;
    public void deleteCoinDesc(String code) throws RuntimeException;
    public List<CoinDesc> getAllCoinDesc();
    public CoinDesc getCoinDesc(String code) throws RuntimeException;
    public BpisPojo getBpisFromCoinDesk() throws RuntimeException;
    public void fillBpisWithCoinDesc(BpisPojo bpisPojo);
}
