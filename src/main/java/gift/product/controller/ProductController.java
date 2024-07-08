package gift.product.controller;

import gift.product.dto.ClientProductDto;
import gift.product.dto.LoginMember;
import gift.product.model.Product;
import gift.product.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProductAll(HttpServletRequest request) {
        LoginMember loginMember = getLoginMember(request);
        List<Product> productAll = productService.getProductAll(loginMember);
        return ResponseEntity.ok(productAll);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable(name = "id") Long id,
        HttpServletRequest request) {
        LoginMember loginMember = getLoginMember(request);
        Product product = productService.getProduct(id, loginMember);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/insert")
    public ResponseEntity<Product> insertProduct(@Valid @RequestBody ClientProductDto productDto,
        HttpServletRequest request) {
        LoginMember loginMember = getLoginMember(request);
        Product responseProduct = productService.insertProduct(productDto, loginMember);

        return ResponseEntity.ok(responseProduct);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(name = "id") Long id,
        @Valid @RequestBody ClientProductDto productDto, HttpServletRequest request) {
        LoginMember loginMember = getLoginMember(request);
        Product product = productService.updateProduct(id, productDto, loginMember);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(name = "id") Long id,
        HttpServletRequest request) {
        LoginMember loginMember = getLoginMember(request);
        productService.deleteProduct(id, loginMember);

        return ResponseEntity.ok().build();
    }

    private LoginMember getLoginMember(HttpServletRequest request) {
        return new LoginMember((Long) request.getAttribute("memberId"));
    }
}
