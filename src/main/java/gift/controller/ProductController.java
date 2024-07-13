package gift.controller;

import gift.DTO.ProductDTO;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * @param productDTO 저장할 상품 객체
     */
    @PostMapping
    public ProductDTO createProduct(@Valid @RequestBody ProductDTO productDTO) {
        return productService.createProduct(productDTO);
    }

    /**
     * 주어진 ID에 해당하는 상품을 반환함
     *
     * @param id 조회할 상품의 ID
     */
    @GetMapping("/{id}")
    public ProductDTO getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    /**
     * 모든 상품을 반환함
     */
    @GetMapping("/all")
    public List<ProductDTO> getProducts() {
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
     * @param id         갱신할 상품의 ID
     * @param productDTO 갱신할 상품 객체
     */
    @PutMapping("/{id}")
    public ProductDTO updateProduct(@PathVariable Long id,
        @Valid @RequestBody ProductDTO productDTO) {
        return productService.updateProduct(id, productDTO);
    }
}
