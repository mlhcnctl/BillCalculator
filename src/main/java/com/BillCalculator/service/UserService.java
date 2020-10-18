package com.BillCalculator.service;

import com.BillCalculator.auth.TokenManager;
import com.BillCalculator.constant.ErrorCodes;
import com.BillCalculator.dto.*;
import com.BillCalculator.entity.UserEntity;
import com.BillCalculator.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ConfirmationMailService confirmationMailService;
    private final AuthenticationManager authenticationManager;
    private final TokenManager tokenManager;

    public UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest) {

        UserRegisterResponse userRegisterResponse = new UserRegisterResponse();
        ResponseData responseData = new ResponseData();

        boolean isPasswordEqualsConfirmPassword = userRegisterRequest.checkPasswords();

        if (isPasswordEqualsConfirmPassword == false) {
            responseData.setErrorCode(ErrorCodes.FAILED);
            responseData.setErrorExplanation(ErrorCodes.PASSWORD_DOES_NOT_EQUAL_CONFIRM_PASSWORD);
            userRegisterResponse.setResponse(responseData);
            return userRegisterResponse;
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = bCryptPasswordEncoder.encode(userRegisterRequest.getPassword());

        Optional<UserEntity> user = Optional.ofNullable(userRepository.findByUserName(userRegisterRequest.getUserName()));

        if (user.isPresent()) {
            if (user.get().isConfirmed()) {
                responseData.setErrorCode(ErrorCodes.FAILED);
                responseData.setErrorExplanation(ErrorCodes.USER_ALREADY_EXIST);
                userRegisterResponse.setResponse(responseData);
                return userRegisterResponse;
            } else {
                responseData.setErrorCode(ErrorCodes.FAILED);
                responseData.setErrorExplanation(ErrorCodes.USER_EXIST_BUT_MAIL_NOT_CONFIRMED);
                userRegisterResponse.setResponse(responseData);
                return userRegisterResponse;
            }
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(encryptedPassword);
        userEntity.setFullName(userRegisterRequest.getName());
        userEntity.setUserName(userRegisterRequest.getUserName());
        userEntity.setPassword(encryptedPassword);
        userEntity.setPhoneNumber(userRegisterRequest.getPhoneNumber());
        userEntity.setEmail(userRegisterRequest.getEmail());
        userRepository.save(userEntity);

        userEntity = userRepository.findFirstByOrderByCreatedDateDesc();

        confirmationMailService.saveConfirmation(userEntity);

        responseData.setErrorCode(ErrorCodes.SUCCESS);
        responseData.setErrorExplanation(ErrorCodes.SUCCESS_EXPLANATION_USER);
        userRegisterResponse.setResponse(responseData);
        return userRegisterResponse;
    }

    public UserLoginResponse checkLogin(UserLoginRequest userLoginRequest) {

        UserLoginResponse response = new UserLoginResponse();
        ResponseDataForLogin responseDataForLogin = new ResponseDataForLogin();
        String token = null;

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequest.getUserName(),
                    userLoginRequest.getPassword()));
        } catch (Exception ex) {
            responseDataForLogin.setErrorCode(ErrorCodes.FAILED);
            responseDataForLogin.setErrorExplanation(ErrorCodes.USERNAME_OR_PASSWORD_INCORRECT);
            response.setResponse(responseDataForLogin);
            return response;
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        UserEntity user = userRepository.findByUserName(userLoginRequest.getUserName());

        if (!encoder.matches(userLoginRequest.getPassword(), user.getPassword())) {
            responseDataForLogin.setErrorCode(ErrorCodes.FAILED);
            responseDataForLogin.setErrorExplanation(ErrorCodes.PASSWORD_IS_ICORRECT);
            response.setResponse(responseDataForLogin);
            return response;
        }

        token = tokenManager.generateToken(userLoginRequest.getUserName());
        responseDataForLogin.setErrorCode(ErrorCodes.SUCCESS);
        responseDataForLogin.setErrorExplanation(ErrorCodes.LOGIN_SUCCESSFULL);
        responseDataForLogin.setToken(token);
        response.setResponse(responseDataForLogin);
        return response;

    }

}
