package product_service.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import product_service.Entity.Product;
import product_service.Service.ProductService;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public Product create(@RequestBody Product p) {
        return service.create(p);
    }

    @GetMapping("/{id}")
    public Product get(@PathVariable Long id) {
        return service.getProduct(id);
    }
}