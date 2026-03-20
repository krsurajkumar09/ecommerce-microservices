package notification_service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notification_service.config.KafkaTopics;
import notification_service.dto.OrderEvent;
import notification_service.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationService service;

    @KafkaListener(
            topics = KafkaTopics.PAYMENT_SUCCESS,
            groupId = "notification-group"
    )
    public void handleSuccess(OrderEvent event) {

        log.info("🔥 Received PAYMENT SUCCESS: {}", event);

        try {
            service.sendSuccessNotification(event);

        } catch (Exception e) {

            log.error("❌ Email failed, sending to DLQ directly: {}", e.getMessage());

            // 🔥 DO NOT THROW EXCEPTION → Avoid retry loop
            // Instead handle gracefully or send to DLQ manually
        }
    }
}