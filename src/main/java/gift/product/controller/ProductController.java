package gift.product.controller;

import gift.product.dto.ProductDto;
import gift.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    public void addProduct(@Valid @RequestBody ProductDto productDto) {
        productService.save(productDto);
    }

    @PutMapping("/edit/{product_id}")
    public void editProduct(@PathVariable Long product_id, @Valid @RequestBody ProductDto productDto) {
        productService.update(product_id, productDto);
    }

    @DeleteMapping("/delete/{product_id}")
    public void deleteProduct(@PathVariable Long product_id) {
        productService.deleteById(product_id);
    }

    /** 페이지네이션을 위한 새로운 엔드포인트
     * 특정 페이지와 크기 요청: /api/products/paged?page=1&size=5
     * 페이지 번호: 0
     * 페이지 크기: 10
     * 정렬: 이름을 기준으로 오름차순 정렬 (기본값) **/
    @GetMapping("/paged")
    public Page<ProductDto> getProducts(
            @RequestParam(defaultValue = "0") int page, // 클라이언트가 특정 페이지를 요청할 때 이 파라미터를 사용
            @RequestParam(defaultValue = "10") int size, // 한 페이지에 몇 개의 항목이 표시될지를 정의
            @RequestParam(defaultValue = "name,asc") String[] sort) // 배열 형태. 순서대로 정렬 속성, 정렬 방향 표시
    {
        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort[0]));

        return productService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PutMapping("/{id}")
    public void updateProduct(@PathVariable Long id, @RequestBody @Valid ProductDto productDto) {
        productService.update(id, productDto);
    }
}