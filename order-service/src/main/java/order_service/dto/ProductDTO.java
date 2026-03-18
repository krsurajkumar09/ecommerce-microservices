package order_service.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private int availableQuantity;
    private double price;
}