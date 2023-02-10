package com.cathaybk.coindesk.exception;

public class CoinDescNotFoundException extends RuntimeException{

    public CoinDescNotFoundException(){
        super("指定幣別中文敘述不存在...");
    }

    public CoinDescNotFoundException(String code){
        super("指定幣別:"+code+" 中文敘述不存在...");
    }
}
