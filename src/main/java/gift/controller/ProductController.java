package gift.controller;

import gift.dto.ProductDTO;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 상품 추가,수정,삭제,조회를 위한 api end-point
 * <p>
 * $/api/products
 */
@RequestMapping("/api")
@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 상품 전체 목록 반환
     *
     * @return 상품 DTO
     */
    @GetMapping("/products")
    public List<ProductDTO> getList() {
        List<ProductDTO> dto = productService.readAll();
        return dto;
    }

    /**
     * 새로운 상품 생성
     *
     * @param dto id가 존재하는 상태로 입력되더라도 무시됨.
     */
    @PostMapping("/products")
    public void add(@RequestBody @Valid ProductDTO dto) {
        productService.create(dto);
    }

    /**
     * 기존 상품 수정
     *
     * @param id  수정하고자 하는 상품의 id
     * @param dto 수정하고자 하는 값 이외 null로 지정
     */
    @PutMapping("/products/{id}")
    public void update(@PathVariable Long id, @RequestBody @Valid ProductDTO dto) {
        if (id == null) {
            throw new IllegalArgumentException("id를 입력해주세요");
        }
        changeCheckAndUpdate(id, dto);
    }

    @DeleteMapping("/products/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    private void changeCheckAndUpdate(Long id, ProductDTO dto) {
        if (dto.getName() != null) {
            productService.updateName(id, dto.getName());
        }
        if (dto.getPrice() != null) {
            productService.updatePrice(id, dto.getPrice());
        }
        if (dto.getImageUrl() != null) {
            productService.updateImageUrl(id, dto.getImageUrl());
        }
    }

    @GetMapping("/products/{page}")
    public List<ProductDTO> getPage(@PathVariable int page) {
        return productService.readProduct(page,10);
    }

}