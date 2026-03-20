package payment_service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import payment_service.dto.OrderEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentProducer {

    // ✅ IMPORTANT: Use Object, not String
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendSuccess(OrderEvent event) {
        log.info("Sending PAYMENT SUCCESS for order {}", event.getOrderId());
        kafkaTemplate.send("payment-success", event);
    }

    public void sendFailure(OrderEvent event) {
        log.info("Sending PAYMENT FAILURE for order {}", event.getOrderId());
        kafkaTemplate.send("payment-failed", event);
    }
}