package payment_service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import payment_service.dto.OrderEvent;
import payment_service.service.PaymentService;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentConsumer {

    private final PaymentService paymentService;

    @KafkaListener(topics = "order-created", groupId = "payment-group")
    public void consume(OrderEvent event) {
        log.info("Received Order Event: {}", event);
        paymentService.processPayment(event);
    }
}