package notification_service.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notification_service.model.NotificationMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailUtil {

    private final JavaMailSender mailSender;

    // ✅ Main method using DTO
    public void sendEmail(NotificationMessage msg) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(msg.getRecipient());
            mail.setSubject(msg.getSubject());
            mail.setText(msg.getBody());

            mailSender.send(mail);

            log.info("✅ Email sent to {}", msg.getRecipient());

        } catch (Exception e) {
            log.error("❌ Email sending failed: {}", e.getMessage(), e);
            throw e; // 🔥 Important for retry / debugging
        }
    }

    // ✅ FIXED: Overloaded method now WORKS
    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(body);

            mailSender.send(mail);

            log.info("✅ Email sent to {}", to);

        } catch (Exception e) {
            log.error("❌ Email sending failed: {}", e.getMessage(), e);
            throw e; // 🔥 Important
        }
    }
}