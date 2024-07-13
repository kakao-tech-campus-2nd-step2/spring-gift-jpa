package gift.admin;

import gift.product.Product;
import gift.product.ProductService;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageController {

    private final ProductService productService;

    public AdminPageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "/admin")
    public String adminPage(Model model, Pageable pageable) {
        Page<Product> products = productService.getAllProducts(pageable);

        model.addAttribute("headerText", "Manage products");
        model.addAttribute("jsSrc", "../adminPage.js");
        model.addAttribute("products", products);
        model.addAttribute("page", pageable.getPageNumber() + 1);
        model.addAttribute("totalProductsSize", products.getTotalElements());
        model.addAttribute("currentPageProductSize", products.get().toList().size());
        model.addAttribute("pageLists",
            IntStream.range(1, products.getTotalPages() + 1).boxed().toList());

        return "basicPage";
    }
}
