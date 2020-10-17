package com.BillCalculator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterRequest {

    @NotEmpty(message = "name cannot be null or empty")
    private String name;
    @NotEmpty(message = "username cannot be null or empty")
    private String userName;
    @NotEmpty(message = "password cannot be null or empty")
    private String password;
    @NotEmpty(message = "confirm password cannot be null or empty")
    private String confirmPassword;
    @NotEmpty(message = "phone number cannot be null or empty")
    @Pattern(regexp = "(\\+90|0)[0-9]{10}", message = "the phone number must be 11 digits number and all numbers must be in 0 to 10")
    private String phoneNumber;
    @NotEmpty(message = "email cannot be null or empty")
    @Email(message = "please provide valid e-mail format")
    private String email;

    public boolean checkPasswords() {
        if (password.equals(confirmPassword))
            return true;
        else
            return false;
    }

}
