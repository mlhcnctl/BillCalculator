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
public class ShareholderRequest {

    @NotEmpty(message = "name cannot be null and empty")
    private String name;
    @Pattern(regexp = "(\\+90|0)[0-9]{10}", message = "the phone number must be 10 digits number and all numbers must be in 0 to 10")
    private String phoneNumber;
    @Email(message = "please provide valid e-mail format")
    private String email;
    @NotEmpty(message = "owner username of shareholder cannot be null or empty")
    private String ownerUsernameOfShareHolder;

}
