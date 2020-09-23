package com.BillCalculator.controller;

import com.BillCalculator.dto.UserRegisterRequest;
import com.BillCalculator.dto.UserRegisterResponse;
import com.BillCalculator.entity.ConfirmationMailEntity;
import com.BillCalculator.service.ConfirmationMailService;
import com.BillCalculator.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

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
    public String confirmMail(@RequestParam("token") String token) {

        Optional<ConfirmationMailEntity> optionalConfirmationMailEntity = confirmationMailService.findByToken(token);
        optionalConfirmationMailEntity.ifPresent(userService::confirmUser);

        return "mail confirmation is succesfull!";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
