package com.cathaybk.coindesk.exception.handler;

import com.cathaybk.coindesk.exception.CoinDescNotFoundException;
import com.cathaybk.coindesk.exception.JsonToPojoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice("com.cathaybk.coindesk.controller.CoinController")
public class CoinControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<Object> handle(RestClientException restClientException,
                                         WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "RestClientException:"+restClientException.getMessage());

        return new ResponseEntity<>(body, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(CoinDescNotFoundException.class)
    public ResponseEntity<Object> handle(CoinDescNotFoundException coinDescNotFoundException,
                                         WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "CoinDescNotFoundException:"+ coinDescNotFoundException.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JsonToPojoException.class)
    public ResponseEntity<Object> handle(JsonToPojoException jsonToPojoException,
                                         WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "JsonToPojoException:"+jsonToPojoException.getMessage());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
