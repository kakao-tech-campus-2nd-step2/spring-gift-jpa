package gift.controller;

import gift.common.exception.ProductNotFoundException;
import gift.controller.dto.request.ProductRequest;
import gift.controller.dto.response.ProductResponse;
import gift.model.Product;
import gift.model.repository.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "Product", description = "Product관련 API")
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Operation(summary = "상품 목록 조회", description = "상품 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 목록 조회 성공"),
    })
    @GetMapping()
    public ResponseEntity<List<ProductResponse>> getProductList() {
        List<Product> foundProducts = productRepository.findAll();

        List<ProductResponse> responses = foundProducts.stream()
                .map(ProductResponse::fromModel)
                .toList();

        return ResponseEntity.ok()
                .body(responses);
    }

    @Operation(summary = "상품 상세 조회", description = "상품 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 상세 조회 성공"),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductDetails(@PathVariable("id") Long id) {
        Product foundProduct = productRepository.find(id)
                .orElseThrow(() -> ProductNotFoundException.of(id));

        ProductResponse response = ProductResponse.fromModel(foundProduct);

        return ResponseEntity.ok()
                .body(response);
    }

    @Operation(summary = "상품 등록", description = "상품을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "상품 등록 성공"),
    })
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void saveProduct(@RequestBody @Valid ProductRequest newProduct) {
        productRepository.save(newProduct.toModel());
    }

    @Operation(summary = "상품 수정", description = "상품을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 수정 성공"),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void modifyProduct(@PathVariable("id") Long id, @RequestBody @Valid ProductRequest modifyProduct) {
        Product foundProduct = productRepository.find(id)
                .orElseThrow(() -> ProductNotFoundException.of(id));

        productRepository.save(modifyProduct.toModel(id));
    }

    @Operation(summary = "상품 삭제", description = "상품을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "상품을 찾을 수 없음")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable("id") Long id) {
        Product foundProduct = productRepository.find(id)
                .orElseThrow(() -> ProductNotFoundException.of(id));

        foundProduct.delete();
        productRepository.save(foundProduct);
    }
}
