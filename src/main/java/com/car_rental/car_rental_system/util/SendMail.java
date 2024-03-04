package com.car_rental.car_rental_system.util;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author Dilan
 * @created 04/03/2024 - 07:24 pm
 */

@AllArgsConstructor
@NoArgsConstructor
@Service
public class SendMail {

    private static final Logger log = LoggerFactory.getLogger(SendMail.class);

    private JavaMailSender mailSender;

    public void sendEmail(String to, String text) {
        if (to == null || text == null) {
            log.error("Failed to send email empty text or mail");
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject("Job Junction OTP");
            message.setText(text);
            mailSender.send(message);
            log.info("Email sent successfully to: {}", to);
        } catch (Exception e) {
            log.error("Failed to send email to {}: {}", to, e.getMessage());
        }
    }

}
