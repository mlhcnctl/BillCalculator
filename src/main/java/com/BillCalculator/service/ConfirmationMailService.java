package com.BillCalculator.service;

import com.BillCalculator.entity.ConfirmationMailEntity;
import com.BillCalculator.entity.UserEntity;
import com.BillCalculator.repository.ConfirmationMailRepository;
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

    private ConfirmationMailRepository confirmationMailRepository;
    private JavaMailSender javaMailSender;

    public Optional<ConfirmationMailEntity> findByToken(String token) {
        return confirmationMailRepository.findConfirmationMailEntitiesByConfirmationToken(token);
    }

    public void saveConfirmation(UserEntity userEntity) {

        String confirmationToken = UUID.randomUUID().toString();

        ConfirmationMailEntity confirmationMailEntity = new ConfirmationMailEntity();
        confirmationMailEntity.setUserEntity(userEntity);
        confirmationMailEntity.setConfirmationToken(confirmationToken);
        confirmationMailRepository.save(confirmationMailEntity);

        sendConfirmationMail(userEntity.getEmail(), confirmationToken);

    }

    public void sendConfirmationMail(String email, String token) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Mail Confirmation Link!");
        mailMessage.setFrom("mail@gmail.com");
        mailMessage.setText(
                "Thank you for registering. Please click on the link to activate your account. " + "http://localhost:8080/register/confirm?token="
                        + token);

        sendEmail(mailMessage);

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
