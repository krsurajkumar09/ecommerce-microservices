package product_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import product_service.dto.ProductDTO;
import product_service.entity.Product;
import product_service.repository.ProductRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository repo;

    // ✅ GET PRODUCT (Feign + Cache)
    @Cacheable(value = "products", key = "#id")
    public ProductDTO getProduct(Long id) {

        log.info("Fetching product with id {}", id);

        Product product = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setAvailableQuantity(product.getAvailableQuantity());
        dto.setPrice(product.getPrice());

        return dto;
    }

    // ✅ CREATE PRODUCT
    public Product create(Product p) {
        return repo.save(p);
    }

    // 🔥 NEW: REDUCE STOCK (for future Kafka consumer)
    @Transactional
    public void reduceStock(Long productId, int quantity) {

        Product product = repo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getAvailableQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        repo.reduceQuantity(productId, quantity);
    }
}