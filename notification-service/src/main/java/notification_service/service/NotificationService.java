package notification_service.service;

import notification_service.dto.OrderEvent;
import notification_service.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private EmailUtil emailUtil;

    // ✅ Payment Success Notification
    public void sendSuccessNotification(OrderEvent event) {
        try {
            emailUtil.sendEmail(
                    "user@example.com",
                    "Payment Success",
                    "Payment successful for order " + event.getOrderId()
            );

            log.info("✅ SUCCESS email sent for order {}", event.getOrderId());

        } catch (Exception e) {
            log.error("❌ Failed to send SUCCESS email for order {}", event.getOrderId(), e);
        }
    }

    // ❌ Payment Failure Notification
    public void sendFailureNotification(OrderEvent event) {
        try {
            emailUtil.sendEmail(
                    "user@example.com",
                    "Payment Failed",
                    "Payment failed for order " + event.getOrderId()
            );

            log.info("✅ FAILURE email sent for order {}", event.getOrderId());

        } catch (Exception e) {
            log.error("❌ Failed to send FAILURE email for order {}", event.getOrderId(), e);
        }
    }
}