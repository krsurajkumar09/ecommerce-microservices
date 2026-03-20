package product_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import product_service.entity.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // ✅ Find by name (future use)
    Optional<Product> findByName(String name);

    // 🔥 Reduce stock (important for Kafka consumer later)
    @Modifying
    @Query("UPDATE Product p SET p.availableQuantity = p.availableQuantity - :qty WHERE p.id = :id")
    void reduceQuantity(Long id, int qty);

    // ✅ Get stock (optional optimization)
    @Query("SELECT p.availableQuantity FROM Product p WHERE p.id = :id")
    int getAvailableQuantity(Long id);
}