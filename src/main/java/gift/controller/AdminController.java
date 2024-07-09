package gift.controller;

import gift.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    ProductService productService;

    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    //기본화면
    @GetMapping
    public String admin() {
        return "admin";

    }

    //상품 관리 화면
    @GetMapping("/product-management")
    public String productManage(Model model) {
        model.addAttribute("products", productService.getAllProducts());

        return "product-manage";

    }


    //상품 추가 화면
    @GetMapping("/product-add")
    public String productAdd() {
        return "product-add";

    }

}
