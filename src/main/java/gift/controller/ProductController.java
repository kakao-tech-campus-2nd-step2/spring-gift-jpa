/**
 * ProductController Class
 * 사용 : Product에 대한 CRUD 처리를 수행하고, 해당 결과를 보여줄 View를 가져온다
 * 기능 : 상품 목록 불러오기, 상품 추가, 삭제, 수정
 */
package gift.controller;

import gift.DTO.Product.ProductRequest;
import gift.DTO.Product.ProductResponse;
import gift.DTO.User.UserRequest;
import gift.security.AuthenticateMember;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    /*
     * 상품 목록 출력
     * GET 요청에 따라 Json 형식 배열을 반환
     */
    @GetMapping("/api/products")
    public ResponseEntity<List<ProductResponse>> getProducts() {
        List<ProductResponse> products = productService.readAllProduct();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /*
     * 상품 추가
     * POST 요청에 따라 다음과 같은 결과 값을 반환
     * 성공 시,  : 실제로 DB에 상품을 등록, 상태코드 201 Created
     * + 제한 조건 : 글자수 15자 이하, 특수문자 제한, 제품명에 카카오가 들어가면 Exception
     */
    @PostMapping("/api/products")
    public ResponseEntity<Void> createProduct(@Valid @RequestBody ProductRequest product){
        productService.createProduct(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /*
     * 상품 삭제
     * DELETE 요청에 따라 다음과 같은 결과 값을 반환
     * 삭제 성공 : 200 OK
     */
    @DeleteMapping("/api/products/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") Long id){
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    /*
     * 상품 수정
     * PUT 요청에 따라 다음과 같은 결과 값을 반환
     * 리소스 URI에 대해, 해당 URI가 배정된 객체가 없으면 : 404 NOT_FOUND
     * 수정 성공 : 200 OK
     */
    @PutMapping("/api/products/{productId}")
    public ResponseEntity<Void>modifyProduct(@PathVariable("productId") Long id, @Valid @RequestBody ProductRequest product){
        if(!productService.isDuplicate(id)){
            return new ResponseEntity<>((HttpStatus.NOT_FOUND));
        }
        productService.updateProduct(product, id);
        return new ResponseEntity<>((HttpStatus.OK));
    }
}