package notification_service.util;

import lombok.RequiredArgsConstructor;
import notification_service.model.NotificationMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailUtil {

    private final JavaMailSender mailSender;

    public void sendEmail(NotificationMessage msg) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(msg.getRecipient());
        mail.setSubject(msg.getSubject());
        mail.setText(msg.getBody());

        mailSender.send(mail);
    }
}