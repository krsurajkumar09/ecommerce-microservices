package product_service.service;


import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import product_service.dto.ProductDTO;
import product_service.entity.Product;
import product_service.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repo;

    @Cacheable(value = "products", key = "#id")
    public ProductDTO getProduct(Long id) {
        Product product = repo.findById(id).orElseThrow();

        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setAvailableQuantity(product.getAvailableQuantity());
        dto.setPrice(product.getPrice());

        return dto;
    }

    public Product create(Product p) {
        return repo.save(p);
    }

}