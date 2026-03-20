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
    private final PaymentProducer paymentProducer;

    @KafkaListener(
            topics = "order-created",
            groupId = "payment-group"
    )
    public void consume(OrderEvent event) {

        log.info("🔥 EVENT RECEIVED IN PAYMENT SERVICE: {}", event);

        try {
            // ✅ SINGLE SOURCE OF TRUTH
            boolean success = paymentService.processPayment(event);

            if (success) {
                paymentProducer.sendSuccess(event);
            } else {
                paymentProducer.sendFailure(event);
            }

        } catch (Exception e) {

            log.error("🚨 System failure while processing payment", e);

            // 🔥 Retry for system failures only
            throw e;
        }
    }
}