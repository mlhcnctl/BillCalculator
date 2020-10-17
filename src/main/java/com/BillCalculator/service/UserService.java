package com.BillCalculator.service;

import com.BillCalculator.constant.ErrorCodes;
import com.BillCalculator.dto.ResponseData;
import com.BillCalculator.dto.UserRegisterRequest;
import com.BillCalculator.dto.UserRegisterResponse;
import com.BillCalculator.entity.ConfirmationMailEntity;
import com.BillCalculator.entity.UserEntity;
import com.BillCalculator.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ConfirmationMailService confirmationMailService;

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

}
