package gift.controller;

import gift.config.PageConfig;
import gift.dto.product.ProductResponse;
import gift.entity.Product;
import gift.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductViewController {

    private final ProductService productService;

    public ProductViewController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getAllProducts(
        @PageableDefault(
            size = PageConfig.PAGE_PER_COUNT,
            sort = PageConfig.SORT_STANDARD,
            direction = Direction.DESC
        ) Pageable pageable,
        Model model
    ) {
        Page<ProductResponse> products = productService.getAllProducts(pageable);

        model.addAttribute("productPage", products);
        model.addAttribute("maxPage", 10);
        return "product_list";
    }

    @GetMapping("/new")
    public String newProduct(Model model) {
        model.addAttribute("product", Product.builder().build());
        return "product_add_form";
    }

    @GetMapping("edit/{id}")
    public String editProductPage(Model model, @PathVariable Long id) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product_edit_form";
    }

}
