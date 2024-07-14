package gift.admin.controller;

import gift.global.annotation.Products;
import gift.product.dto.ProductResponseDto;
import gift.product.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;

    @Autowired
    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String loadMainPage(@RequestParam(name = "page-no") int pageNumber,
        @RequestParam(name = "sorting-state") int sortingState,
        @Products List<ProductResponseDto> products, Model model) {

        model.addAttribute("products", products);

        // 작업 후에 기존 상태를 보존해야 하므로 model에 넣었습니다.
        model.addAttribute("pageNo", pageNumber);
        model.addAttribute("sortingState", sortingState);

        return "html/admin";
    }

    // edit-product.html을 SSR로 넘겨주는 핸들러
    @GetMapping("/products/{product-id}/edit")
    public String loadEditPage(@PathVariable(name = "product-id") long productId, Model model) {
        ProductResponseDto product = productService.selectProduct(productId);

        model.addAttribute("product", product);
        return "html/edit-product";
    }
}
