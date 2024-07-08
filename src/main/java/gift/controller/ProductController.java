package gift.controller;


import gift.domain.Product;
import gift.service.ProductService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 모든 상품 조회
    @GetMapping
    public String getAllProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    // 상품 추가하는 창
    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "product-form";
    }

    // 상품 추가 후 홈으로 이동
    @PostMapping("/add")
    public String addProduct(@ModelAttribute @Valid Product product) {
        productService.createProduct(product);
        return "redirect:/products";
    }

    // 등록된 상품 수정하는 창
    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "product-form";
    }

    // 등록된 상품을 수정하는 기능
    @PutMapping("/edit/{id}")
    public String editProduct(@PathVariable("id") Long id, @ModelAttribute @Valid Product product) {
        product.setId(id);
        productService.updateProduct(id, product);
        return "redirect:/products";
    }

    // 등록된 상품을 삭제하는 기능
    @DeleteMapping ("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }

}
