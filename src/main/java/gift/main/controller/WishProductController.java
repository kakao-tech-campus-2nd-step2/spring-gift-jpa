package gift.main.controller;

import gift.main.entity.Product;
import gift.main.repository.ProductDao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class UserProductController {
    private final ProductDao productDao;

    public UserProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping()
    public ResponseEntity<?> getProducts() {
        List<Product> products = productDao.selectProductAll();
        Map<String, List<Product>> response = new HashMap<>();
        response.put("products", products);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProducts(@PathVariable(name = "id") Long id) {
        List<Product> products = productDao.selectProductAll();
        Map<String, List<Product>> response = new HashMap<>();
        response.put("products", products);
        return ResponseEntity.ok(response);
    }
}
