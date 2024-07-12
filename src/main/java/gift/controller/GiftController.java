package gift.controller;

import gift.controller.dto.PaginationDTO;
import gift.controller.dto.ProductDTO;
import gift.domain.Product;
import gift.service.GiftService;
import gift.utils.PaginationUtils;
import jakarta.validation.Valid;
import java.util.Arrays;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<Page<Product>> getAllProducts(@ModelAttribute PaginationDTO paginationDTO,
        @RequestHeader("Authorization") String token) {
        Pageable pageable = PaginationUtils.createPageable(paginationDTO, "product");
        Page<Product> allProducts = giftService.getAllProduct(pageable);
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
