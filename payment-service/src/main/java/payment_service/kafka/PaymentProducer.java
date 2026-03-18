package payment_service.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import payment_service.dto.OrderEvent;

@Component
@RequiredArgsConstructor
public class PaymentProducer {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void sendSuccess(OrderEvent event) {
        kafkaTemplate.send("payment-success", event);
    }

    public void sendFailure(OrderEvent event) {
        kafkaTemplate.send("payment-failed", event);
    }
}