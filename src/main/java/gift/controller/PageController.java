package gift.controller;

import gift.domain.Product;
import gift.dto.ProductDto;
import gift.repositories.ProductRepository;
import gift.services.ProductService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {
    @Autowired
    ProductRepository productRepository;

    private final ProductService productService;

    public PageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/") // 주소 매핑
    public String indexPageGet(Model model) {
        List<ProductDto> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/add") // 주소 매핑
    public String addPageGet(Model model) {
        Product product = new Product(null, "", 0.0, "");
        model.addAttribute("product", product);
        return "add";
    }

    @GetMapping("/update") // 주소 매핑
    public String updatePageGet(@RequestParam Long id, Model model) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty())
            product = Optional.of(new Product(null, "", 0.0, ""));
        model.addAttribute("product", product);
        return "update";
    }
}
