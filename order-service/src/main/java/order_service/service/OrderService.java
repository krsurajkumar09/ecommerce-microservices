package order_service.service;

import lombok.RequiredArgsConstructor;
import order_service.client.ProductClient;
import order_service.dto.OrderRequest;
import order_service.dto.ProductDTO;
import order_service.entity.Order;
import order_service.entity.OrderLine;
import order_service.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductClient productClient;
    private final OrderRepository orderRepository;

    public Order createOrder(OrderRequest request) {

        ProductDTO product = productClient.getProduct(request.getProductId());

        if (product.getAvailableQuantity() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setReference(UUID.randomUUID().toString());

        OrderLine line = new OrderLine();
        line.setProductId(request.getProductId());
        line.setQuantity(request.getQuantity());
        line.setOrder(order);

        order.setOrderLines(List.of(line));

        return orderRepository.save(order);
    }
}