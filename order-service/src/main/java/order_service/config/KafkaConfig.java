package order_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    // 🔥 Centralized topic names (avoid hardcoding in producer/consumer)
    public static final String ORDER_CREATED_TOPIC = "order-created";

    // ✅ Create only required topic
    @Bean
    public NewTopic orderCreatedTopic() {
        return new NewTopic(ORDER_CREATED_TOPIC, 1, (short) 1);
    }
}