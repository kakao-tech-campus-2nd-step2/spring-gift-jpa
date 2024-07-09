package gift.controller;

import gift.service.ProductService;
import gift.dto.ProductDto;
import gift.dto.ProductUpdateDto;
import jakarta.validation.Valid;
import gift.vo.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    /**
     * 상품 조회 - 전체
     * @return 전체 상품 목록
     */
    @GetMapping()
    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }

    /**
     * 상품 조회 - 한 개
     * @param id 조회할 상품의 ID
     * @return 조회한 product
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable(value = "id") Long id) {
        Product product = service.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    /**
     * 상품 추가
     * @param productDto Product로 변환 후 처리
     * @return ResponseEntity로 Response 받음
     */
    @PostMapping()
    public ResponseEntity<Void> addProduct(@Valid @RequestBody ProductDto productDto) {
        Boolean result = service.addProduct(productDto.toProduct());
        if (result) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 상품 수정
     * @param productUpdateDto 수정할 상품 (JSON 형식)
     *    ㄴ받는 Product에 id 필드 값이 포함 되어 있어야 한다.
     * @return 수정된 상품
     */
    @PutMapping()
    public ResponseEntity<Void> updateProduct(@Valid @RequestBody ProductUpdateDto productUpdateDto) {
        Boolean result = service.updateProduct(productUpdateDto.toProduct());
        if (result) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    /**
     * 상품 삭제
     * @param id 삭제할 상품의 ID
     * @return HTTP State - No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(value = "id") Long id) {
        Boolean result = service.deleteProduct(id);
        if (result) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
