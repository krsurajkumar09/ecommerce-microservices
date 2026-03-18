package product_service.DTO;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private int availableQuantity;
    private double price;
}