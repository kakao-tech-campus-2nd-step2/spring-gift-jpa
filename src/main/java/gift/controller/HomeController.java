package gift.controller;


import org.springframework.ui.Model;
import gift.entity.Product;
import gift.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/view")
public class HomeController {

    private final ProductService productService;

    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/home")
    public String showHomeForm() {
        return "home";
    }


    @GetMapping("/admin/products")
    public String showProductsPage() {
        return "products"; // 뷰 이름
    }

    @GetMapping("/admin/products/data")
    @ResponseBody
    public Map<String, Object> getProducts(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        Page<Product> productPage = productService.getProducts(page, size);
        Map<String, Object> response = new HashMap<>();
        response.put("content", productPage.getContent());
        response.put("currentPage", productPage.getNumber());
        response.put("totalPages", productPage.getTotalPages());
        response.put("hasNext", productPage.hasNext());
        response.put("hasPrevious", productPage.hasPrevious());
        return response;
    }


    @GetMapping("/wishlist")
    public String wishlistForm() {
        return "wishlist";
    }


}
