package payment_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import payment_service.dto.OrderEvent;
import payment_service.entity.Payment;
import payment_service.repository.PaymentRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository repository;

    /**
     * Processes payment and returns result
     * true  -> SUCCESS
     * false -> FAILED
     */
    public boolean processPayment(OrderEvent event) {

        // ✅ Idempotency check (VERY IMPORTANT)
        if (repository.findByOrderId(event.getOrderId()).isPresent()) {
            log.warn("⚠️ Payment already processed for order: {}", event.getOrderId());
            return false; // already processed (avoid duplicate events)
        }

        log.info("💳 Processing payment for order {}", event.getOrderId());

        Payment payment = new Payment();
        payment.setOrderId(event.getOrderId());
        payment.setReference(UUID.randomUUID().toString());
        payment.setAmount(100.0);

        // 🔥 Controlled logic (replace with real payment gateway later)
        boolean success = event.getOrderId() % 2 != 0;

        if (success) {
            payment.setStatus("SUCCESS");
        } else {
            payment.setStatus("FAILED");
        }

        repository.save(payment);

        log.info("✅ Payment completed for order {} with status {}",
                event.getOrderId(), payment.getStatus());

        return success;
    }
}