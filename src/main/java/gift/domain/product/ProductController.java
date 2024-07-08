package gift.domain.product;

import gift.domain.user.dto.UserInfo;
import gift.global.jwt.JwtAuthorization;
import gift.global.response.ResponseMaker;
import gift.global.response.ResultResponseDto;
import gift.global.response.SimpleResultResponseDto;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 상품 추가
     */
    @PostMapping
    public ResponseEntity<SimpleResultResponseDto> createProduct(
        @Valid @ModelAttribute ProductDTO productDTO) {
        productService.createProduct(productDTO);
        return ResponseMaker.createSimpleResponse(HttpStatus.CREATED, "상품이 추가되었습니다.");
    }

    /**
     * 전체 상품 조회
     */
    @GetMapping
    public ResponseEntity<ResultResponseDto<List<Product>>> getProducts() {
        List<Product> products = productService.getProducts();
        // 성공 시
        return ResponseMaker.createResponse(HttpStatus.OK, "전체 목록 상품을 조회했습니다.", products);
    }

    /**
     * 상품 수정
     */
    @PutMapping("/{id}")
    public ResponseEntity<SimpleResultResponseDto> updateProduct(@PathVariable("id") Long id,
        @Valid @RequestBody ProductDTO productDTO) {
        productService.updateProduct(id, productDTO);
        return ResponseMaker.createSimpleResponse(HttpStatus.OK, "상품을 수정했습니다.");
    }


    /**
     * 선택된 상품들 삭제
     */
    @DeleteMapping
    public ResponseEntity<SimpleResultResponseDto> deleteSelectedProducts(
        @RequestBody List<Long> productIds) {
        productService.deleteProductsByIds(productIds);
        return ResponseMaker.createSimpleResponse(HttpStatus.OK, "선택된 상품들을 삭제했습니다.");
    }

    /**
     * 상품 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<SimpleResultResponseDto> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return ResponseMaker.createSimpleResponse(HttpStatus.OK, "상품이 삭제되었습니다.");
    }

}
