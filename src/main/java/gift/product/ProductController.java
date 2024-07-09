package gift.product;


import gift.Exception.ErrorResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductService productService;

    public ProductController(ProductRepository productRepository, ProductService productService){
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @GetMapping()
    public String listProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("newProduct", new Product()); // 새 상품 객체
        model.addAttribute("product", new Product()); // 편집을 위한 빈 객체*/
        return "admin"; // Thymeleaf 템플릿 이름
    }

    @PostMapping("/post")
    public ResponseEntity<HttpStatus> createProduct(@Valid @ModelAttribute Product newProduct) {
        return ResponseEntity.ok(productService.createProduct(newProduct));
    }

    @PutMapping("/update")
    public ResponseEntity<HttpStatus> updateProduct(@Valid @ModelAttribute Product changeProduct) {
        return ResponseEntity.ok(productService.updateProduct(changeProduct));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(400).body(new ErrorResponse(ex.getMessage()));
    }

}