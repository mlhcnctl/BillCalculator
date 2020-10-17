package com.BillCalculator.controller;

import com.BillCalculator.auth.TokenManager;
import com.BillCalculator.dto.ConfirmMailResponse;
import com.BillCalculator.dto.UserLoginRequest;
import com.BillCalculator.dto.UserRegisterRequest;
import com.BillCalculator.dto.UserRegisterResponse;
import com.BillCalculator.entity.ConfirmationMailEntity;
import com.BillCalculator.service.ConfirmationMailService;
import com.BillCalculator.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ConfirmationMailService confirmationMailService;
    private final AuthenticationManager authenticationManager;
    private final TokenManager tokenManager;

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> register(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        return ResponseEntity.ok(userService.registerUser(userRegisterRequest));
    }

    @GetMapping("/register/confirm")
    public ResponseEntity<ConfirmMailResponse> confirmMail(@RequestParam("token") String token) {
        return ResponseEntity.ok(confirmationMailService.confirmUser(token));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequest userLoginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequest.getUserName(),
                    userLoginRequest.getPassword()));
            return ResponseEntity.ok(tokenManager.generateToken(userLoginRequest.getUserName()));
        } catch (Exception ex) {
            throw ex;
        }
    }

}
