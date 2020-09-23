package com.BillCalculator.service;

import com.BillCalculator.constant.ErrorCodes;
import com.BillCalculator.dto.ResponseData;
import com.BillCalculator.dto.UserRegisterRequest;
import com.BillCalculator.dto.UserRegisterResponse;
import com.BillCalculator.entity.ConfirmationMailEntity;
import com.BillCalculator.entity.UserEntity;
import com.BillCalculator.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ConfirmationMailService confirmationMailService;

    public UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest) {

        UserRegisterResponse userRegisterResponse = new UserRegisterResponse();
        ResponseData responseData = new ResponseData();

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = bCryptPasswordEncoder.encode(userRegisterRequest.getPassword());

        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(encryptedPassword);
        userEntity.setFullName(userRegisterRequest.getName());
        userEntity.setUserName(userRegisterRequest.getUserName());
        userEntity.setPassword(encryptedPassword);
        userEntity.setPhoneNumber(userRegisterRequest.getPhoneNumber());
        userEntity.setEmail(userRegisterRequest.getEmail());
        userEntity.setConfirmed(false);
        userRepository.save(userEntity);

        userEntity = userRepository.findFirstByOrderByCreatedDateDesc();

        confirmationMailService.saveConfirmation(userEntity);

        responseData.setErrorCode(ErrorCodes.SUCCESS);
        responseData.setErrorExplanation(ErrorCodes.SUCCESS_EXPLANATION_USER);
        userRegisterResponse.setResponse(responseData);
        return userRegisterResponse;
    }

    public void confirmUser(ConfirmationMailEntity confirmationMailEntity) {
        UserEntity userEntity = confirmationMailEntity.getUserEntity();
        userEntity.setConfirmed(true);
        userRepository.save(userEntity);

        confirmationMailService.deleteConfirmationMailEntity(confirmationMailEntity.getId());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> optionalUserEntity = userRepository.findByUserName(username);
        return (UserDetails) optionalUserEntity.orElseThrow(() -> new UsernameNotFoundException(MessageFormat.format("User with username {0} cannot be found.", username)));

        //bu kismi kayit olurken daha once kullanici kayit olmus mu seklinde kontrol amacli kullanacagiz..
        //

    }
}
