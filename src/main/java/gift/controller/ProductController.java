package gift.controller;

import gift.exception.ProductNotFoundException;
import gift.model.Product;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String getAllMyProducts(Model model) {
        var productList = productService.getAllProducts();
        model.addAttribute("productList", productList);
        return "getproducts";
    }

    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "All products retrieved successfully.");
        response.put("products", products);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable(name = "id") Long id, Model model) {
        Map<String, Object> response = new HashMap<>();
        try {
            Product product = productService.getProductById(id);
            model.addAttribute("productDto", product);
            return "getproducts";
        } catch (ProductNotFoundException ex) {
            response.put("message", ex.getMessage());
            model.addAttribute("errorMessage", response.get("message"));
            return "getproducts";
        }
    }

    @GetMapping("/product/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "addproductform";
    }

    @PostMapping("/product/add")
    public String createProduct(@Valid @ModelAttribute(name = "product") Product product) {
        productService.createProduct(product);
        return "redirect:/";
    }

    @GetMapping(value = "/product/update/{id}")
    public String showUPdateProductForm(@PathVariable("id") Long id, Model model) {
        var product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "updateproductform";
    }

    @PostMapping(value = "/product/update")
    public String updateProduct(@Valid @ModelAttribute(name = "product") Product product) {
        var updatedProduct = productService.updateProduct(product);
        return "redirect:/";
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> patchProduct(@PathVariable Long id,
        @RequestBody Map<String, Object> updates) {
        Map<String, Object> response = new HashMap<>();
        if (productService.patchProduct(id, updates) != null) {
            Product updatedProduct = productService.getProductById(id);
            response.put("message", "Product patched successfully.");
            response.put("product", updatedProduct);
            return ResponseEntity.ok(response);
        }
        response.put("message", "Failed to patch product.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @PatchMapping
    public ResponseEntity<Map<String, Object>> patchProducts(
        @RequestBody List<Map<String, Object>> updatesList) {
        List<Optional<Product>> updatedProducts = productService.patchProducts(updatesList);
        Map<String, Object> response = new HashMap<>();
        int originalCount = updatesList.size();
        int updateCount = updatedProducts.size();

        response.put("updatedProducts", updatedProducts);

        if (updateCount == originalCount) {
            response.put("message", "All products patched successfully.");
            return ResponseEntity.ok(response);
        }

        if (updateCount > 0) {
            response.put("message", "Some products patched successfully.");
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(response);
        }

        response.put("message", "No products patched.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @GetMapping("/product/delete/{id}")
    public String showDeleteProductForm(@PathVariable("id") Long id, Model model) {
        if (!productService.deleteProduct(id)) {
            model.addAttribute("errorMessage", "잘못됨");
        }
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable Long id) {
        boolean success = productService.deleteProduct(id);
        Map<String, Object> response = new HashMap<>();
        if (success) {
            response.put("message", "Product deleted successfully.");
            return ResponseEntity.noContent().build();
        }
        response.put("message", "Failed to delete product.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
