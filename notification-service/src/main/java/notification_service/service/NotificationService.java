package notification_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notification_service.dto.OrderEvent;
import notification_service.util.EmailUtil;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final EmailUtil emailUtil;

    public void sendSuccessNotification(OrderEvent event) {

        log.info("📧 Sending SUCCESS email for order {}", event.getOrderId());

        // 🔥 If this fails → Kafka retry + DLQ will handle
        emailUtil.sendEmail(
                "user@gmail.com",
                "Payment Success",
                "Payment successful for order " + event.getOrderId()
        );

        log.info("✅ SUCCESS email sent for order {}", event.getOrderId());
    }

    public void sendFailureNotification(OrderEvent event) {

        log.info("📧 Sending FAILURE email for order {}", event.getOrderId());

        emailUtil.sendEmail(
                "user@gmail.com",
                "Payment Failed",
                "Payment failed for order " + event.getOrderId()
        );

        log.info("✅ FAILURE email sent for order {}", event.getOrderId());
    }
}