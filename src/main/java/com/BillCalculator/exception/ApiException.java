package com.BillCalculator.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiException {

    private String message;
    private int httpStatusCode;
    private HttpStatus httpStatus;
    private ZonedDateTime timestamp;

}
