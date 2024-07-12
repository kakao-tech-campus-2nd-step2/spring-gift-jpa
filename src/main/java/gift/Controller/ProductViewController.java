package gift.Controller;

import gift.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductViewController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/view")
    public String viewAllProducts(Model model) {
        model.addAttribute("products", productService.findAllProducts());
        return "product-list";
    }

    @GetMapping("/products/view/{id}")
    public String viewProductById(@PathVariable Long id, Model model) {
        productService.findProductById(id).ifPresent(product -> model.addAttribute("product", product));
        return "product-detail";
    }
}
