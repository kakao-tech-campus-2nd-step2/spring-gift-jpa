package gift.web.controller.view;

import gift.service.ProductService;
import gift.web.dto.form.CreateProductFormDto;
import gift.web.dto.response.ReadAllProductsResponse;
import gift.web.dto.response.ReadProductResponse;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view/products")
public class ProductViewController {

    private final ProductService productService;

    public ProductViewController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public String readAdminPage(Model model) {
        ReadAllProductsResponse allProductsResponse = productService.readAllProducts();
        List<ReadProductResponse> products = allProductsResponse.getProducts();
        model.addAttribute("products", products);
        return "admin";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("product", new CreateProductFormDto());
        return "form/add-product-form";
    }

    @GetMapping("/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        ReadProductResponse product = productService.searchProduct(id);
        model.addAttribute("product", product);
        return "form/edit-product-form";
    }
}
