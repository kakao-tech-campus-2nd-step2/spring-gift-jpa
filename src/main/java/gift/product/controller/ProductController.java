package gift.product.controller;

import gift.product.dto.ProductReqDto;
import gift.product.dto.ProductResDto;
import gift.product.message.ProductInfo;
import gift.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<Page<ProductResDto>> getProducts(Pageable pageable) {
        Page<ProductResDto> productResDtos = productService.getProducts(pageable);

        return ResponseEntity.ok(productResDtos);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductResDto> getProduct(@PathVariable("productId") Long productId) {
        ProductResDto productResDto = productService.getProduct(productId);

        return ResponseEntity.ok(productResDto);
    }

    @PostMapping("/products")
    public ResponseEntity<ProductResDto> addProduct(@Valid @RequestBody ProductReqDto productReqDto) {
        ProductResDto productResDto = productService.addProduct(productReqDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(productResDto);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable("productId") Long productId, @Valid @RequestBody ProductReqDto productReqDto) {
        productService.updateProduct(productId, productReqDto);

        return ResponseEntity.ok(ProductInfo.PRODUCT_UPDATE_SUCCESS);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);

        return ResponseEntity.ok(ProductInfo.PRODUCT_DELETE_SUCCESS);
    }
}
