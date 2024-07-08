package gift.controller;

import gift.domain.ProductDomain;
import gift.model.Product;
import gift.dao.ProductDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/api")
public class ProductController {
    private final ProductDomain productDomain;

    public ProductController(ProductDomain productDomain) {
        this.productDomain = productDomain;
    }

    // 모든 상품 반환
    @GetMapping("/getAllProducts")
    public String getAllProductsController(Model model) {
        model.addAttribute("productList", productDomain.getAllProducts());
        return "index";
    }

    // id 상품 하나 반환
    @GetMapping("/getProduct/{id}")
    @ResponseBody
    public Product getProductByIdController(@PathVariable Long id) {
        return productDomain.getProductById(id);
    }

    // 상품 추가
    @PostMapping("/postProduct")
    public String postProductController(@ModelAttribute Product product) {
        productDomain.postProduct(product);
        return "redirect:/api/getAllProducts";
    }

    // 상품 삭제
    @GetMapping("/deleteProduct/{id}")
    public String deleteProductController(@PathVariable Long id) {
        productDomain.deleteProduct(id);
        return "redirect:/api/getAllProducts";
    }

    // 상품 업데이트
    @PostMapping("/updateProduct/{id}")
    public String updateProductController(@PathVariable Long id, @ModelAttribute Product newProduct) {
        productDomain.updateProduct(id, newProduct);
        return "redirect:/api/getAllProducts";
    }
}