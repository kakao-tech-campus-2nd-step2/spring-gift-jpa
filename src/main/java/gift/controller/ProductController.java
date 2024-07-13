package gift.controller;

import gift.dto.ProductRequest;
import gift.dto.ProductResponse;
import gift.model.MemberRole;
import gift.service.ProductService;
import gift.service.page.PageService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final PageService pageService;

    public ProductController(ProductService productService, PageService pageService) {
        this.productService = productService;
        this.pageService = pageService;
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addProduct(@Valid @RequestBody ProductRequest productRequest, @RequestAttribute("memberRole") String memberRole) {
        var product = productService.addProduct(productRequest, MemberRole.valueOf(memberRole));
        return ResponseEntity.created(URI.create("/api/products/" + product.id())).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest productRequest) {
        productService.updateProduct(id, productRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        var product = productService.getProduct(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        pageService.pageValidation(pageable);
        var products = productService.getProducts(pageable);
        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
