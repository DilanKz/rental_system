package com.car_rental.car_rental_system.util;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
public class SendMail {
    private JavaMailSender mailSender;

    /**
     * Sends an email with the given recipient email address and text.
     *
     * @param to   The recipient email address.
     * @param text The email text.
     */
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
