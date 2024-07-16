package gift.controller;

import gift.dto.ProductDto;
import gift.model.product.Product;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import gift.service.ProductService;


@RequestMapping("/api/products")
@Controller
@Validated
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Void> addNewProduct(@Valid @RequestBody ProductDto productDto) {
        if (productService.addNewProduct(productDto)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDto productDto) {
        if(productService.updateProduct(id, productDto)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/edit/{id}")
    public String moveToEditProduct(@PathVariable Long id, Model model) {
        Product product = productService.selectProduct(id);
        model.addAttribute("product", product);
        return "editProduct";
    }

    @GetMapping
    public String getProductList(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Product> productPage = productService.selectAllProducts(PageRequest.of(page, 20));
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("currentPage", page);
        return "productManage";
    }

    @GetMapping("/add")
    public String movtoAddProduct() {
        return "addProduct";
    }

    @DeleteMapping("/{id}")
    public String DeleteProduct(@PathVariable Long id){
        productService.DeleteProduct(id);
        return "productManage";
    }

    @PutMapping("/purchase/{id}")
    public ResponseEntity<String> purchaseProduct(@PathVariable Long id, @RequestParam int amount) {
        if (productService.purchaseProduct(id, amount)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}