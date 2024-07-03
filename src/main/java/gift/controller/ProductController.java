package gift.controller;

import gift.domain.Product;
import gift.dto.requestDTO.ProductRequestDTO;
import gift.dto.responseDTO.ProductListResponseDTO;
import gift.dto.responseDTO.ProductResponseDTO;
import gift.repository.ProductRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
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
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    public ResponseEntity<ProductListResponseDTO> getAllProducts(){
        List<ProductResponseDTO> productResponseDTOList = productRepository.selectAllProduct()
            .stream()
            .map(ProductResponseDTO::of)
            .toList();

        ProductListResponseDTO productListResponseDTO = new ProductListResponseDTO(productResponseDTOList);
        return ResponseEntity.ok(productListResponseDTO);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductResponseDTO> getOneProduct(@PathVariable("id") Long productId){
        Product product = productRepository.selectProduct(productId)
            .orElseThrow(() -> new NoSuchElementException("id가 잘못되었습니다."));
        return ResponseEntity.ok(ProductResponseDTO.of(product));
    }

    @PostMapping("/product")
    public ResponseEntity<Long> addProduct(@RequestBody ProductRequestDTO productPostRequestDTO){
        Long productId = Product.getNextId();
        Product product = new Product(productId, productPostRequestDTO.name(),
            productPostRequestDTO.price(), productPostRequestDTO.imageUrl());
        productRepository.insertProduct(product);
        return ResponseEntity.ok(product.getId());
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Long> updateProduct(@PathVariable("id") Long productId, @RequestBody
        ProductRequestDTO productRequestDTO){
        Product product = productRepository.selectProduct(productId)
            .orElseThrow(() -> new NoSuchElementException("id가 잘못되었습니다."));

        product.update(productRequestDTO.name(), productRequestDTO.price(),
            productRequestDTO.imageUrl());
        productRepository.updateProduct(product);
        return ResponseEntity.ok(product.getId());
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Long> deleteProduct(@PathVariable("id") Long productId){
        Product product = productRepository.selectProduct(productId)
            .orElseThrow(() -> new NoSuchElementException("id가 잘못되었습니다."));
        productRepository.deleteProduct(productId);
        return ResponseEntity.ok(product.getId());
    }
}
