package com.cathaybk.coindesk.exception.handler;

import com.cathaybk.coindesk.controller.CoinController;
import com.cathaybk.coindesk.exception.CoinDescFoundException;
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

@ControllerAdvice(assignableTypes = {CoinController.class})
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
    public ResponseEntity<Object> handle(CoinDescNotFoundException coinDescNotFoundException){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "CoinDescNotFoundException:"+ coinDescNotFoundException.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CoinDescFoundException.class)
    public ResponseEntity<Object> handle(CoinDescFoundException coinDescFoundException){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "CoinDescFoundException:"+ coinDescFoundException.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonToPojoException.class)
    public ResponseEntity<Object> handle(JsonToPojoException jsonToPojoException,
                                         WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "JsonToPojoException:"+jsonToPojoException.getMessage());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
