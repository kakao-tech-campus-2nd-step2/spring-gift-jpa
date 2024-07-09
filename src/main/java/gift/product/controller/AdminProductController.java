package gift.product.controller;

import gift.product.model.Product;
import gift.product.service.ProductService;
import gift.product.validation.ProductValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@Controller
@RequestMapping("/admin/product")
public class AdminProductController {

    private final ProductService productService;
    private final AtomicLong idCounter = new AtomicLong();
    private final ProductValidation productValidation;

    @Autowired
    public AdminProductController(ProductService productService, ProductValidation productValidation) {
        this.productService = productService;
        this.productValidation = productValidation;
    }

    @GetMapping("/list")
    public String showProductList(Model model) {
        System.out.println("[ProductController] showProductList()");
        model.addAttribute("productList", productService.getAllProducts());
        return "product";
    }

    @GetMapping("/register")
    public String showProductForm(Model model) {
        System.out.println("[ProductController] showProductForm()");
        model.addAttribute("product", new Product(idCounter.incrementAndGet(), "", 0, ""));
        return "product-form";
    }

    @PostMapping()
    public String registerProduct(@Valid @ModelAttribute Product product, BindingResult bindingResult, Model model) {
        System.out.println("[ProductController] registerProduct()");
        if (bindingResult.hasErrors()) {
            model.addAttribute("product", product);
            return "product-form";
        }
        productService.registerProduct(product);
        return "redirect:/admin/product/list";
    }

    @GetMapping("/update/{id}")
    public String updateProductForm(@PathVariable Long id, Model model) {
        System.out.println("[ProductController] updateProductForm()");
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product-update-form";
    }

    @PutMapping()
    public String updateProduct(@ModelAttribute Product product, BindingResult bindingResult, Model model) {
        System.out.println("[ProductController] updateProduct()");
        if (bindingResult.hasErrors()) {
            model.addAttribute("product", product);
            return "product-form";
        }
        productService.updateProduct(product);
        return "redirect:/admin/product/list";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id, Model model) {
        System.out.println("[ProductController] deleteProduct()");
        if(productService.existsById(id))
            productService.deleteProduct(id);
        return "redirect:/admin/product/list";
    }

    @GetMapping("/search")
    public String searchProduct(@RequestParam("keyword") String keyword, Model model) {
        System.out.println("[ProductController] searchProduct()");
        model.addAttribute("searchResults", productService.searchProducts(keyword));
        model.addAttribute("keyword", keyword);
        return "product-search-list";
    }
}
