package notification_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notification_service.dto.OrderEvent;
import notification_service.model.NotificationMessage;
import notification_service.util.EmailUtil;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final EmailUtil emailUtil;

    public void sendSuccessNotification(OrderEvent event) {

        NotificationMessage message = new NotificationMessage(
                "user@gmail.com",
                "Order Payment Successful",
                "Your payment for order " + event.getOrderId() + " was successful."
        );

        emailUtil.sendEmail(message);
    }

    public void sendFailureNotification(OrderEvent event) {

        NotificationMessage message = new NotificationMessage(
                "user@gmail.com",
                "Order Payment Failed",
                "Payment failed for order " + event.getOrderId()
        );

        emailUtil.sendEmail(message);
    }
}