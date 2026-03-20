package order_service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order_service.config.KafkaConfig;
import order_service.dto.OrderEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProducer {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void sendOrderCreatedEvent(OrderEvent event) {
        log.info("Sending order event to Kafka: {}", event);
        kafkaTemplate.send(KafkaConfig.ORDER_CREATED_TOPIC, event);
    }
}