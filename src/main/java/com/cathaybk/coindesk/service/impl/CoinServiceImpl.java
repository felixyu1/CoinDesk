package com.cathaybk.coindesk.service.impl;


import com.cathaybk.coindesk.dao.CoinDescDao;
import com.cathaybk.coindesk.exception.CoinDescFoundException;
import com.cathaybk.coindesk.exception.CoinDescNotFoundException;
import com.cathaybk.coindesk.exception.JsonToPojoException;
import com.cathaybk.coindesk.model.BpiPojo;
import com.cathaybk.coindesk.model.BpisPojo;
import com.cathaybk.coindesk.model.CoinDesc;
import com.cathaybk.coindesk.service.CoinService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class CoinServiceImpl implements CoinService {

    @Autowired
    private CoinDescDao coinDescDao;

    @Autowired
    private Environment env;

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void createCoinDesc(CoinDesc coinDesc) throws RuntimeException {

        CoinDesc c = coinDescDao.findById(coinDesc.getCode()).orElse(null);

        if(c == null) {
            coinDescDao.save(coinDesc);
        }else{
            throw new CoinDescFoundException(coinDesc.getCode());
        }
    }

    @Override
    public void updateCoinDesc(String code, CoinDesc coinDesc)
            throws RuntimeException {

        CoinDesc c = getCoinDesc(code);

        if(c != null){
            coinDescDao.save(coinDesc);
        }
    }

    @Override
    public void deleteCoinDesc(String code)
            throws RuntimeException{

        CoinDesc c = getCoinDesc(code);

        if(c != null) {
            coinDescDao.deleteById(code);
        }
    }

    @Override
    public List<CoinDesc> getAllCoinDesc() {
        List<CoinDesc> coinDescList = coinDescDao.findAll();

        if(coinDescList.size() > 0){
            return coinDescList;
        }else {
            return null;
        }
    }

    @Override
    public CoinDesc getCoinDesc(String code) throws RuntimeException{

        CoinDesc coinDesc = coinDescDao.findById(code).orElse(null);

        if(coinDesc != null) {
            return coinDesc;
        }else{
            throw new CoinDescNotFoundException(code);
        }
    }

    @Override
    public BpisPojo getBpisFromCoinDesk()
            throws RuntimeException {
        BpisPojo bpisPojo = null;

        ResponseEntity<String> response
                = restTemplate.getForEntity(
                        env.getProperty("coindesk.bpi.url"), String.class);

        String bpiJson = response.getBody();
        System.out.println(bpiJson);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            bpisPojo = objectMapper.readValue(bpiJson, BpisPojo.class);
        } catch (Exception e){
            e.printStackTrace();
        }

        if(bpisPojo != null){
            return bpisPojo;
        }else{
            throw new JsonToPojoException(bpiJson);
        }

    }

    @Override
    public void fillBpisWithCoinDesc(BpisPojo bpisPojo) {

        for (String key: bpisPojo.getBpi().keySet()) {
            BpiPojo bpip = bpisPojo.getBpi().get(key);
            CoinDesc coinDesc = null;
            try{
                coinDesc = getCoinDesc(bpip.getCode());
            }catch(CoinDescNotFoundException exception){
                exception.printStackTrace();
            }

            if(coinDesc != null) {
                bpip.setZhtwDesc(coinDesc.getZhtwDesc());
            }else{
                bpip.setZhtwDesc("");
            }
        }

    }
}
