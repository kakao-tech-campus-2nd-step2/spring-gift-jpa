package gift.product.presentation;

import gift.product.application.ProductService;
import gift.product.domain.Product;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wishlist")
public class WishListManageController {

    private final ProductService productService;

    @Autowired
    public WishListManageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public ResponseEntity<List<Product>> getProducts(Model model) {
        List<Product> productList = productService.getProduct();
        return ResponseEntity
            .ok(productList);
    }

    @PostMapping("")
    public ResponseEntity<Long> addProduct(@RequestBody CreateProductRequestDTO createProductRequestDTO) {
        Long productId = productService.addProduct(createProductRequestDTO);
        return ResponseEntity
            .ok(productId);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    public record CreateProductRequestDTO(String name, Double price, String imageUrl) {

    }
}
