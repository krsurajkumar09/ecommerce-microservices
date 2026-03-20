package product_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import product_service.dto.ProductDTO;
import product_service.entity.Product;
import product_service.service.ProductService;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    // ✅ CREATE PRODUCT
    @PostMapping
    public ProductDTO create(@RequestBody Product product) {

        Product saved = service.create(product);

        ProductDTO dto = new ProductDTO();
        dto.setId(saved.getId());
        dto.setName(saved.getName());
        dto.setAvailableQuantity(saved.getAvailableQuantity());
        dto.setPrice(saved.getPrice());

        return dto;
    }

    // ✅ GET PRODUCT BY ID (USED BY FEIGN)
    @GetMapping("/{id}")
    public ProductDTO get(@PathVariable Long id) {
        return service.getProduct(id);
    }

    // ✅ HEALTH CHECK (useful for debugging)
    @GetMapping("/health")
    public String health() {
        return "Product Service is running!";
    }
}