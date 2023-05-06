package com.evoucherapp.evoucher.exception;

import com.evoucherapp.evoucher.dto.MessageInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Object> handleCityNotFoundException(
            NoDataFoundException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessageInfo().getMessage());
        return new ResponseEntity<>(body, ex.getHttpStatus());
    }

    @ExceptionHandler(DataExistException.class)
    public ResponseEntity<Object> handleCityNotFoundException(
            DataExistException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessageInfo().getMessage());
        return new ResponseEntity<>(body, ex.getHttpStatus());
    }

    @ExceptionHandler(UnAuthorizationException.class)
    public ResponseEntity<Object> handleCityNotFoundException(
            UnAuthorizationException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessageInfo().getMessage());
        return new ResponseEntity<>(body, ex.getHttpStatus());
    }
}
