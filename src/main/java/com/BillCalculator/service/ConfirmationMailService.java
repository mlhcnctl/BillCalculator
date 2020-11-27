package com.BillCalculator.service;

import com.BillCalculator.constant.ErrorCodes;
import com.BillCalculator.dto.ConfirmMailResponse;
import com.BillCalculator.dto.ResponseData;
import com.BillCalculator.entity.ConfirmationMailEntity;
import com.BillCalculator.entity.UserEntity;
import com.BillCalculator.repository.ConfirmationMailRepository;
import com.BillCalculator.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ConfirmationMailService {

    private final ConfirmationMailRepository confirmationMailRepository;
    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;

    public ConfirmMailResponse confirmUser(String token) {

        ConfirmMailResponse confirmMailResponse = new ConfirmMailResponse();
        ResponseData responseData = new ResponseData();

        Optional<ConfirmationMailEntity> optionalConfirmationMailEntity = findByToken(token);

        if (!optionalConfirmationMailEntity.isPresent()) {
            responseData.setErrorCode(ErrorCodes.FAILED);
            responseData.setErrorExplanation(ErrorCodes.TOKEN_IS_NOT_FOUND);
            confirmMailResponse.setResponse(responseData);
            return confirmMailResponse;
        }

        if (optionalConfirmationMailEntity.get().isMailConfirmed() == true) {
            responseData.setErrorCode(ErrorCodes.FAILED);
            responseData.setErrorExplanation(ErrorCodes.MAIL_ALREADY_CONFIRMED);
            confirmMailResponse.setResponse(responseData);
            return confirmMailResponse;
        }

        UserEntity userEntity = optionalConfirmationMailEntity.get().getUserEntity();
        userEntity.setConfirmed(true);
        userRepository.save(userEntity);

        confirmMail(optionalConfirmationMailEntity.get());

        responseData.setErrorCode(ErrorCodes.SUCCESS);
        responseData.setErrorExplanation(ErrorCodes.SUCCESS_EXPLANATION_CONFIRM_MAIL);
        confirmMailResponse.setResponse(responseData);
        return confirmMailResponse;
    }

    public Optional<ConfirmationMailEntity> findByToken(String token) {
        return confirmationMailRepository.findConfirmationMailEntitiesByConfirmationToken(token);
    }

    public void saveConfirmation(UserEntity userEntity) {

        String confirmationToken = UUID.randomUUID().toString();

        ConfirmationMailEntity confirmationMailEntity = new ConfirmationMailEntity();
        confirmationMailEntity.setUserEntity(userEntity);
        confirmationMailEntity.setConfirmationToken(confirmationToken);
        confirmationMailRepository.save(confirmationMailEntity);

        try {
            sendConfirmationMail(userEntity.getEmail(), confirmationToken);
        } catch (Exception ex) {
            throw ex;
        }

    }

    public void sendConfirmationMail(String email, String token) {

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(email);
            mailMessage.setSubject("Mail Confirmation Link!");
            mailMessage.setFrom("mail@gmail.com");
            mailMessage.setText(
                    "Thank you for registering. Please click on the link to activate your account. " + "http://localhost:8080/register/confirm?token="
                            + token);

            sendEmail(mailMessage);
        } catch (Exception ex) {
            throw ex;
        }

    }

    @Async
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }

    public void confirmMail(ConfirmationMailEntity confirmationMailEntity) {
        confirmationMailEntity.setMailConfirmed(true);
        confirmationMailRepository.save(confirmationMailEntity);
    }
}
