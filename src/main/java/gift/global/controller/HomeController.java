package gift.global.controller;

import gift.domain.product.ProductService;
import gift.domain.product.Product;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping( "/api")
public class HomeController {

    private final ProductService productService;

    @Autowired
    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 홈 화면 렌더링 (thymeleaf)
     */
    @GetMapping
    String homePage(Model model) {
        // 현재 상품 목록 조회
        List<Product> products = productService.getProducts();
        model.addAttribute("products", products);
        return "index";
    }
}
