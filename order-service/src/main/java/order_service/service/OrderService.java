package order_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order_service.client.ProductClient;
import order_service.dto.OrderEvent;
import order_service.dto.OrderRequest;
import order_service.dto.ProductDTO;
import order_service.entity.Order;
import order_service.entity.OrderLine;
import order_service.kafka.OrderProducer;
import order_service.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderProducer producer;

    @Transactional
    public Order createOrder(OrderRequest request) {

        // ✅ Validate input
        if (request.getQuantity() <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }

        // ✅ Call Product Service safely
        ProductDTO product;
        try {
            product = productClient.getProduct(request.getProductId());
        } catch (Exception e) {
            throw new RuntimeException("Product not found");
        }

        // ✅ Stock validation
        if (product.getAvailableQuantity() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }

        // ✅ Create Order
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setReference(UUID.randomUUID().toString());

        OrderLine line = new OrderLine();
        line.setProductId(request.getProductId());
        line.setQuantity(request.getQuantity());
        line.setOrder(order);

        order.setOrderLines(List.of(line));

        Order savedOrder = orderRepository.save(order);

        // 🔥 Kafka Event (safe)
        try {
            OrderEvent event = new OrderEvent();
            event.setOrderId(savedOrder.getId());
            event.setProductId(request.getProductId());
            event.setQuantity(request.getQuantity());

            producer.sendOrderCreatedEvent(event);

            log.info("Order event sent to Kafka for orderId {}", savedOrder.getId());

        } catch (Exception e) {
            log.error("Kafka failed for orderId {}: {}", savedOrder.getId(), e.getMessage());
        }

        return savedOrder;
    }
}