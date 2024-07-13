package gift.controller;

import gift.dto.ProductDTO;
import gift.entity.Product;
import gift.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProductAdminController {
    @Autowired
    private ProductService productService;

    @GetMapping("/admin/products")
    public String adminProducts(Model model) {
        List<ProductDTO.WithOptionDTO> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return ("admin/products");
    }

    @GetMapping("/admin/add")
    public String adminProductsAdd(Model model) {
        return ("admin/add");
    }

    @GetMapping("/admin/modify")
    public String adminProductsModify(Model model) {
        return ("admin/modify");
    }

    @GetMapping("/admin/delete")
    public String adminProductsDelete(Model model) {
        return ("admin/delete");
    }
}
