package gift;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.pattern.PathPattern;

import static gift.Path.*;


@Controller
@RequestMapping("/api/manage/products")
public class ProductManageController {

    private final ProductService productService;
    public ProductManageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String retrieveProduct(Model model) {
        model.addAttribute("products", productService.getProduct());
        return MANAGE_PRODUCT_PAGE;
    }

    @PostMapping("/delete/{productId}")
    public String deleteProduct(@PathVariable("productId") Long productId) {
        productService.deleteTheProduct(productId);
        return redirect(MANAGE_PRODUCT);
    }

    @GetMapping("/update/{productId}")
    public String editProductForm(@PathVariable("productId") Long productId, Model model) {
        Product product = productService.getOneProduct(productId);
        model.addAttribute("product", product);
        return PRODUCT_UPDATE_FORM_PAGE;
    }

    @PostMapping("/update/{productId}")
    public String updateProduct(@PathVariable("productId") Long productId, @ModelAttribute("product") Product updatedProduct) {
        productService.updateProductInfo(productId, updatedProduct);
        return redirect(MANAGE_PRODUCT);
    }

    @GetMapping("/add")
    public String addProductForm(Model model) {
        model.addAttribute("product", new Product());
        return PRODUCT_ADDITION_FROM_PAGE;
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute("product") Product product) {
        productService.addNewProduct(product);
        return redirect(MANAGE_PRODUCT);
    }

    private String redirect(String path) {
        return "redirect:" + path;
    }

}
