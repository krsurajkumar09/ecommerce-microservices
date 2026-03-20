package payment_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import payment_service.dto.OrderEvent;
import payment_service.entity.Payment;
import payment_service.repository.PaymentRepository;

import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository repository;
    private final payment_service.kafka.PaymentProducer producer;

    public void processPayment(OrderEvent event) {

        if (repository.findByOrderId(event.getOrderId()).isPresent()) {
            log.warn("Payment already processed for order: {}", event.getOrderId());
            return;
        }

        log.info("Processing payment for order {}", event.getOrderId());

        Payment payment = new Payment();
        payment.setOrderId(event.getOrderId());
        payment.setReference(UUID.randomUUID().toString());
        payment.setAmount(100.0);

        boolean success = new Random().nextBoolean();

        if (success) {
            payment.setStatus("SUCCESS");
            producer.sendSuccess(event);
        } else {
            payment.setStatus("FAILED");
            producer.sendFailure(event);
        }

        repository.save(payment);

        log.info("Payment completed for order {} with status {}",
                event.getOrderId(), payment.getStatus());
    }
}