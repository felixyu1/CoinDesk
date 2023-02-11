package com.cathaybk.coindesk.exception;

public class CoinDescFoundException extends RuntimeException{

    public CoinDescFoundException(){
        super("指定幣別中文敘述已存在...");
    }

    public CoinDescFoundException(String code){
        super("指定幣別:"+code+" 中文敘述已存在...");
    }
}
