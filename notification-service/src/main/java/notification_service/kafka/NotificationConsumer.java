package notification_service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notification_service.dto.OrderEvent;
import notification_service.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationService service;

    @KafkaListener(topics = "payment-success", groupId = "notification-group")
    public void handleSuccess(OrderEvent event) {
        log.info("Received payment success: {}", event);
        service.sendSuccessNotification(event);
    }

    @KafkaListener(topics = "payment-failed", groupId = "notification-group")
    public void handleFailure(OrderEvent event) {
        log.info("Received payment failed: {}", event);
        service.sendFailureNotification(event);
    }
}