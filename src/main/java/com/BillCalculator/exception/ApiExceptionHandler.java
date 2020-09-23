package com.BillCalculator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.time.ZoneId;

@RestControllerAdvice
public class ApiExceptionHandler {

    // buraya request validation hatalari duser
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleApiRequestOfValidationException(MethodArgumentNotValidException e) {
        Integer sizeOfErrors = e.getBindingResult().getAllErrors().size();
        String message = new String();
        if (sizeOfErrors == 1) {
            message = (e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        } else {
            for (int i=0; i < sizeOfErrors; i++) {
                message += e.getBindingResult().getAllErrors().get(i).getDefaultMessage();
                message = message + " || ";
            }
        }

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        int httpStatusCode = HttpStatus.BAD_REQUEST.value();
        ApiException apiException = new ApiException(
                message,
                httpStatusCode,
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, badRequest);
    }

    // buraya yanlis girilen yani Json formatinda gelmeyen istek hatalari duser
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<Object> handleApiRequestOfJsonParseError(HttpMessageNotReadableException e) {

        String message = "JSON parse error : Unexpected character";

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        int httpStatusCode = HttpStatus.BAD_REQUEST.value();
        ApiException apiException = new ApiException(
                message,
                httpStatusCode,
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException e) {

        String message = "Username not found";

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        int httpStatusCode = HttpStatus.BAD_REQUEST.value();
        ApiException apiException = new ApiException(
                message,
                httpStatusCode,
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, badRequest);
    }

}
