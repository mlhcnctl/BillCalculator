package com.BillCalculator.controller;

import com.BillCalculator.dto.*;
import com.BillCalculator.service.ConfirmationMailService;
import com.BillCalculator.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ConfirmationMailService confirmationMailService;

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> register(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        return ResponseEntity.ok(userService.registerUser(userRegisterRequest));
    }

    @GetMapping("/register/confirm")
    public ResponseEntity<ConfirmMailResponse> confirmMail(@RequestParam("token") String token) {
        return ResponseEntity.ok(confirmationMailService.confirmUser(token));
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        return  ResponseEntity.ok(userService.checkLogin(userLoginRequest));
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hi");
    }

}
