package gift.controller;

import gift.domain.ProductDTO;
import gift.service.ProductService;
import gift.service.ProductService.ProductServiceStatus;
import java.util.Collection;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/products")
public class ProductController {
    // Service와 의존성 추가
    @Autowired
    private ProductService productService;

    // 상품 추가(Create)
    @PostMapping
    public ResponseEntity<ProductServiceStatus> addProduct(@RequestBody ProductDTO productDTO) {
        ProductServiceStatus response = productService.createProduct(productDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 단일 상품 조회(Read)
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> selectProduct(@PathVariable Long id) {
        Optional<ProductDTO> productDTO = productService.getProduct(id);
        return productDTO
            .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 전체 상품 조회(Read)
    @GetMapping
    public ResponseEntity<Collection<ProductDTO>> selectAllProducts(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Collection<ProductDTO> products = productService.getAllProducts(pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // 상품 수정(Update)
    @PutMapping("/{id}")
    public ProductServiceStatus updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return productService.editProduct(id, productDTO);
    }

    // 상품 삭제(Delete)
    @DeleteMapping("/{id}")
    public ProductServiceStatus deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }
}
