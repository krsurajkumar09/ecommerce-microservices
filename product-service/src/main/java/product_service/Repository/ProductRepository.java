package product_service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import product_service.Entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}