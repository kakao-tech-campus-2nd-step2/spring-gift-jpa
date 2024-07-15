package gift.domain.product;

import gift.global.response.ResponseMaker;
import gift.global.response.ResultResponseDto;
import gift.global.response.SimpleResultResponseDto;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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
import org.springframework.web.bind.annotation.RequestParam;
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
     * 전체 상품 조회 - 페이징
     */
    @GetMapping
    public ResponseEntity<ResultResponseDto<Page<Product>>> getProductsByPageAndSort(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "sort", defaultValue = "id_asc") String sort
    ) {
        int size = 10; // default
        Sort sortObj = getSortObject(sort);
        Page<Product> products = productService.getProductsByPageAndSort(page, size, sortObj);
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

    private Sort getSortObject(String sort) {
        switch (sort) {
            case "price_asc":
                return Sort.by(Sort.Direction.ASC, "price");
            case "price_desc":
                return Sort.by(Sort.Direction.DESC, "price");
            case "name_asc":
                return Sort.by(Sort.Direction.ASC, "name");
            case "name_desc":
                return Sort.by(Sort.Direction.DESC, "name");
            default:
                return Sort.by(Sort.Direction.ASC, "id");
        }
    }
}
