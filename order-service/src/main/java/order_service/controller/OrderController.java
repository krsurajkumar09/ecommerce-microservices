package order_service.controller;

import lombok.RequiredArgsConstructor;
import order_service.dto.OrderRequest;
import order_service.entity.Order;
import order_service.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // ✅ CREATE ORDER
    @PostMapping
    public Order createOrder(@RequestBody OrderRequest request) {
        return orderService.createOrder(request);
    }

    // ✅ OPTIONAL: GET ALL ORDERS
    @GetMapping
    public String test() {
        return "Order Service is working!";
    }
}