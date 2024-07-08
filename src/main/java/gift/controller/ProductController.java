package gift.controller;


import gift.domain.Product;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    ProductService productService;

    //생성자 주입
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //전체 product 목록 조회
    @GetMapping
    public List<Product> getProduct() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")

    public Product getProductById(@PathVariable("id") long id) {
        return productService.getProductById(id);
    }

    //product 추가
    @PostMapping

    public ResponseEntity<String> addProduct(@RequestBody @Valid Product product) {
        productService.saveProduct(product);
        return new ResponseEntity<>("OK", HttpStatus.CREATED);

    }

    //product 수정
    @PatchMapping("/{id}")
    public ResponseEntity<String> editProduct(@PathVariable("id") Long id,
        @RequestBody @Valid Product product) {
        productService.updateProduct(product, id);


        return new ResponseEntity<>("product edit success", HttpStatus.OK);

    }

    //product 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>("product delete success", HttpStatus.NO_CONTENT);
    }

}
