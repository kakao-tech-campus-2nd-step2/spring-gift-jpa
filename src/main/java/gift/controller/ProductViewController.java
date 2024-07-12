package gift.controller;

import gift.domain.model.enums.ProductSortBy;
import gift.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductViewController {

    private final ProductService productService;

    public ProductViewController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String home(Model model,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "ID_DESC") ProductSortBy sortBy) {
        Page<?> productPage = productService.getAllProducts(page, sortBy);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("sortBy", sortBy);
        return "index";
    }
}