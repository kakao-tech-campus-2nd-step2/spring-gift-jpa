package gift.controller;

import gift.common.exception.ProductNotFoundException;
import gift.model.product.Product;
import gift.model.product.ProductRequest;
import gift.model.product.ProductResponse;
import gift.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
    private final ProductRepository productRepository;

    AdminController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/admin/product")
    public String registerProductForm() {
        return "addProduct";
    }

    @PostMapping("/admin/product")
    public String registerProduct(ProductRequest productRequest) {
        Product product = productRepository.save(productRequest.toEntity());
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/products")
    public String getAllProducts(Model model) {
        List<ProductResponse> productList = productRepository.findAll()
            .stream().map(ProductResponse::from).toList();
        model.addAttribute("products", productList);
        return "productList";
    }

    @GetMapping("/admin/product/{id}")
    public ProductResponse getProduct(@PathVariable("id") Long id) {
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        return ProductResponse.from(product);
    }

    @GetMapping("/product")
    public String updateProductForm(@RequestParam("id") Long id) {
        return "editProduct";
    }

    @PutMapping("/admin/product/{id}")
    public String updateProduct(@PathVariable("id") Long id, @RequestBody ProductRequest productRequest) {
        Product product = productRepository.save(productRequest.toEntity());
        return "redirect:/admin/products";
    }

    @DeleteMapping("/admin/product/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productRepository.deleteById(id);
        return "redirect:/admin/products";
    }
}
