package gift.controller;

import gift.DTO.ProductDTO;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
     * 사용자 ID를 통해 사용자의 상품 목록을 가져옵니다.
     *
     * @param page      페이지 번호, 기본값은 0
     * @param size      페이지 크기, 기본값은 10
     * @param criteria  정렬 기준, 기본값은 createdAt
     * @param direction 정렬 방향, 기본값은 desc
     * @return ProductDTO 목록을 포함한 ResponseEntity
     */
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getWishListsByUserId(
        @RequestParam(required = false, defaultValue = "0", value = "page") int page,
        @RequestParam(required = false, defaultValue = "10", value = "size") int size,
        @RequestParam(required = false, defaultValue = "createdAt", value = "criteria") String criteria,
        @RequestParam(required = false, defaultValue = "desc", value = "direction") String direction) {
        Pageable pageable = PageRequest.of(page, size,
            Sort.by(Sort.Direction.valueOf(direction.toUpperCase()), criteria));
        List<ProductDTO> productIds = productService.getAllProducts(pageable);
        return ResponseEntity.ok(productIds);
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
