package gift.controller;

import gift.dto.ProductResponseDto;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import gift.entity.Product;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    //생성자 주입 권장
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

//    @GetMapping
//    public ResponseEntity<List<ProductResponseDto>> getProducts() {
//        return ResponseEntity.ok(productService.findAll());
//    }
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getProducts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productService.findAll(pageable);
        List<ProductResponseDto> productList = productPage.stream()
            .map(product -> new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl()
            ))
            .collect(Collectors.toList());
        return new ResponseEntity<>(productList,HttpStatus.OK);
    }



    @PostMapping
    public ResponseEntity<String> addProducts(@Valid @RequestBody Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }

        if (productService.addProduct(product)!=-1L) {
            return new ResponseEntity<>("상품 추가 완료", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("이미존재하는 상품 id", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> modifyProducts(@PathVariable("id") long id, @Valid @RequestBody  Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        if (productService.updateProduct(product)!=-1L) {
            return new ResponseEntity<>("상품 수정 완료", HttpStatus.OK);
        }
        return new ResponseEntity<>("존재하지 않는 상품 id", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProducts(@PathVariable("id") long id) {
        if (productService.deleteProduct(id)!=-1L) {
            return new ResponseEntity<>("상품삭제 완료", HttpStatus.OK);
        }
        return new ResponseEntity<>("없는 상품", HttpStatus.BAD_REQUEST);
    }





}