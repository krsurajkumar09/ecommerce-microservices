package notification_service.dto;

import lombok.Data;

@Data
public class OrderEvent {
    private Long orderId;
    private Long productId;
    private int quantity;
}