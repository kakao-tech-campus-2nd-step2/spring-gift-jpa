package gift.product.presentation.restcontroller;

import gift.product.presentation.dto.RequestProductDto;
import gift.product.presentation.dto.RequestProductIdsDto;
import gift.product.presentation.dto.ResponsePagingProductDto;
import gift.product.presentation.dto.ResponseProductDto;
import gift.product.business.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseProductDto> getProduct(@PathVariable("id") Long id) {
        var productDto = productService.getProduct(id);
        var responseProductDto = ResponseProductDto.from(productDto);
        return ResponseEntity.ok(responseProductDto);
    }

    @GetMapping
    public ResponseEntity<ResponsePagingProductDto> getProductsByPage(
        @PageableDefault(size = 20, sort = "modifiedDate", direction = Sort.Direction.DESC) Pageable pageable,
        @RequestParam(name = "size", required = false) Integer size) {
        if (size != null) {
            if (size < 1 || size > 100) {
                throw new IllegalArgumentException("size는 1~100 사이의 값이어야 합니다.");
            }
            pageable = PageRequest.of(pageable.getPageNumber(), size, pageable.getSort());
        }
        var productPagingDto = productService.getProductsByPage(pageable);
        var responsePagingProductDto = ResponsePagingProductDto.from(productPagingDto);
        return ResponseEntity.ok(responsePagingProductDto);
    }

    @PostMapping
    public ResponseEntity<Long> createProduct(
        @RequestBody @Valid RequestProductDto requestProductDto) {
        var productRegisterDto = requestProductDto.toProductRegisterDto();
        Long createdId = productService.createProduct(productRegisterDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateProduct(
        @RequestBody @Valid RequestProductDto requestProductDto, @PathVariable("id") Long id) {
        var productRegisterDto = requestProductDto.toProductRegisterDto();
        Long updatedId = productService.updateProduct(productRegisterDto, id);
        return ResponseEntity.ok(updatedId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteProduct(@PathVariable("id") Long id) {
        Long deletedId = productService.deleteProduct(id);
        return ResponseEntity.ok(deletedId);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProducts(@RequestBody @Valid RequestProductIdsDto ids) {
        productService.deleteProducts(ids.productIds());
        return ResponseEntity.ok().build();
    }

}
