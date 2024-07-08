package gift.controller;

import gift.domain.Product;
import gift.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static gift.constant.Path.*;


@Controller
@RequestMapping("/api/manage/products")
public class ProductManageController {

    private final ProductService productService;
    public ProductManageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String retrieveProduct(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return MANAGE_PRODUCT_PAGE;
    }

    @PostMapping("/delete/{productId}")
    public String deleteProduct(@PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);
        return redirect(MANAGE_PRODUCT);
    }

    @GetMapping("/update/{productId}")
    public String editProductForm(@PathVariable("productId") Long productId, Model model) {
        Product product = productService.getProduct(productId);
        model.addAttribute("product", product);
        return PRODUCT_UPDATE_FORM_PAGE;
    }

    @PostMapping("/update/{productId}")
    public String updateProduct(@PathVariable("productId") Long productId, @ModelAttribute("product") Product updatedProduct) {
        productService.updateProduct(productId, updatedProduct);
        return redirect(MANAGE_PRODUCT);
    }

    @GetMapping("/add")
    public String addProductForm(Model model) {
        model.addAttribute("product", new Product());
        return PRODUCT_ADDITION_FROM_PAGE;
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute("product") Product product) {
        productService.addProduct(product);
        return redirect(MANAGE_PRODUCT);
    }

    private String redirect(String path) {
        return "redirect:" + path;
    }

}
