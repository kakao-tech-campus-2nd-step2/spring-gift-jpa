package gift.controller;

import gift.auth.LoginUser;
import gift.domain.Product;
import gift.domain.User;
import gift.dto.requestDTO.ProductRequestDTO;
import gift.dto.responseDTO.ProductListResponseDTO;
import gift.dto.responseDTO.ProductResponseDTO;
import gift.repository.ProductRepository;
import gift.service.AuthService;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
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
    private final AuthService authService;

    public ProductController(ProductService productService, AuthService authService) {
        this.productService = productService;
        this.authService = authService;
    }

    @GetMapping("/products")
    public ResponseEntity<ProductListResponseDTO> getAllProducts(){
        ProductListResponseDTO productListResponseDTO = productService.getAllProducts();
        return ResponseEntity.ok(productListResponseDTO);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductResponseDTO> getOneProduct(@PathVariable("id") Long productId){
        ProductResponseDTO productResponseDTO = productService.getOneProduct(productId);
        return ResponseEntity.ok(productResponseDTO);
    }

    @PostMapping("/product")
    public ResponseEntity<Long> addProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO,
        @LoginUser User user){

        authService.authorizeAdminUser(user, productRequestDTO.name());
        Long productId = productService.addProduct(productRequestDTO);
        return ResponseEntity.ok(productId);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Long> updateProduct(@PathVariable("id") Long productId, @RequestBody
        ProductRequestDTO productRequestDTO){
        Long updatedProductId = productService.updateProduct(productId, productRequestDTO);
        return ResponseEntity.ok(updatedProductId);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Long> deleteProduct(@PathVariable("id") Long productId){
        Long deletedProductId = productService.deleteProduct(productId);
        return ResponseEntity.ok(deletedProductId);
    }
}
