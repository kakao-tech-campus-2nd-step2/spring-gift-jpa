package gift.controller;

import gift.domain.Product;
import gift.dto.CreateProductDto;
import gift.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 상품 추가
    @PostMapping
    public Product createProduct(@RequestBody CreateProductDto productDto) {
        validateProductDto(productDto);
        return productService.createProduct(productDto);
    }

    // 상품 정보 다 적혔는지 유효성 검사
    private void validateProductDto(CreateProductDto productDto) {
        if (productDto.getName() == null || productDto.getPrice() == null || productDto.getDescription() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "상품 정보를 모두 입력해야 합니다.");
        }
    }

}
