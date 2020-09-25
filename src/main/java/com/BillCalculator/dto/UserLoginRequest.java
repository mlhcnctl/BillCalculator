package com.BillCalculator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginRequest {

    @NotEmpty(message = "username cannot be null or empty")
    private String userName;
    @NotEmpty(message = "password cannot be null or empty")
    private String password;

}
