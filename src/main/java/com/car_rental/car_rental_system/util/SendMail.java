package com.car_rental.car_rental_system.util;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author Dilan
 * @created 04/03/2024 - 07:24 pm
 */

@AllArgsConstructor
@Service
@Log4j2
public class SendMail {

    private final JavaMailSender javaMailSender;

    public void sendEmail(String to, String text) {

        log.info("Executing SendMail sendmail with to:{}",to);

        try {

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Job Junction OTP");
            message.setText(text);
            javaMailSender.send(message);

        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", to, e.getMessage());
        }
    }
}
