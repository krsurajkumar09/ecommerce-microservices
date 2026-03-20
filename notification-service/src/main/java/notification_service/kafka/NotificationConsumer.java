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

        log.info("Received PAYMENT SUCCESS: {}", event);

        try {
            service.sendSuccessNotification(event);
        } catch (Exception e) {
            log.error("Failed to send success notification: {}", e.getMessage());
        }
    }

    @KafkaListener(
            topics = KafkaTopics.PAYMENT_FAILED,
            groupId = "notification-group"
    )
    public void handleFailure(OrderEvent event) {

        log.info("Received PAYMENT FAILURE: {}", event);

        try {
            service.sendFailureNotification(event);
        } catch (Exception e) {
            log.error("Failed to send failure notification: {}", e.getMessage());
        }
    }
}