package order_service.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime orderDate;
    private String reference;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonManagedReference   // ✅ FIX
    private List<OrderLine> orderLines;
}