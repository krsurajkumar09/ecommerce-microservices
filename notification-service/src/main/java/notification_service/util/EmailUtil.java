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

    public void sendEmail(NotificationMessage msg) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(msg.getRecipient());
        mail.setSubject(msg.getSubject());
        mail.setText(msg.getBody());

        mailSender.send(mail);

        log.info("✅ Email sent to {}", msg.getRecipient());
    }

    // 🔥 MAIN METHOD USED BY SERVICE
    public void sendEmail(String to, String subject, String body) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(body);

        try {
            mailSender.send(mail);
            log.info("✅ Email sent to {}", to);

        } catch (Exception e) {
            log.error("❌ Email failed for {}: {}", to, e.getMessage());

            // 🔥 VERY IMPORTANT → propagate to Kafka
            throw e;
        }
    }
}