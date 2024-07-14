package gift.Controller;

import gift.DTO.ProductDTO;
import gift.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductViewController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/view")
    public String viewAllProducts(Pageable pageable, Model model) {
        Page<ProductDTO> productPage = productService.getProducts(pageable);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("page", productPage);
        return "product-list";
    }

    @GetMapping("/products/view/{id}")
    public String viewProductById(@PathVariable Long id, Model model) {
        productService.findProductById(id).ifPresent(product -> model.addAttribute("product", product));
        return "product-detail";
    }
}
