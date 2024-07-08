package gift.controller;

import gift.controller.dto.ProductDTO;
import gift.domain.Product;
import gift.service.GiftService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class GiftController {

    private final GiftService giftService;

    public GiftController(GiftService giftService) {
        this.giftService = giftService;
    }


    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> allProducts = giftService.getAllProduct();
        return ResponseEntity.ok(allProducts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = giftService.getProduct(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> postProduct(@Valid @RequestBody ProductDTO productDTO) {
        ProductDTO DTO = giftService.postProducts(productDTO);
        return new ResponseEntity<>(DTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id,
        @Valid @RequestBody ProductDTO productDTO) {
        ProductDTO DTO = giftService.putProducts(productDTO, id);
        return ResponseEntity.ok(DTO);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Long> deleteProduct(@PathVariable Long id) {
        Long i = giftService.deleteProducts(id);
        return ResponseEntity.ok(i);
    }

}
