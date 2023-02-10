package com.cathaybk.coindesk.exception;

public class JsonToPojoException extends RuntimeException{

    public JsonToPojoException(){
        super();
    }

    public JsonToPojoException(String jsonStr){
        super("Json轉Pojo發生問題："+jsonStr);
    }
}
