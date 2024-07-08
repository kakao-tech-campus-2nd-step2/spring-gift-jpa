package gift.controller;

import gift.service.ProductService;
import org.springframework.web.bind.annotation.*;
import gift.model.Product;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 새 상품을 생성하고 맵에 저장함
     *
     * @param product 저장할 상품 객체
     */
    @PostMapping
    public Product createProduct(@Valid @RequestBody Product product) {
        return productService.createProduct(product);
    }

    /**
     * 주어진 ID에 해당하는 상품을 반환함
     *
     * @param id 조회할 상품의 ID
     */
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    /**
     * 모든 상품을 반환함
     */
    @GetMapping("/all")
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }

    /**
     * 주어진 ID에 해당하는 상품을 삭제함
     *
     * @param id 삭제할 상품의 ID
     */
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    /**
     * 주어진 ID에 해당하는 상품을 갱신함
     *
     * @param id      갱신할 상품의 ID
     * @param product 갱신할 상품 객체
     */
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }
}
