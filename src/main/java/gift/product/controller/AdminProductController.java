package gift.product.controller;

import gift.product.model.Product;
import gift.product.service.ProductService;
import gift.product.validation.ProductValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/product")
public class AdminProductController {

    private final ProductService productService;

    @Autowired
    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/list")
    public String showProductList(Model model, Pageable pageable) {
        System.out.println("[ProductController] showProductList()");
        model.addAttribute("productList", productService.getAllProducts(pageable));
        return "product";
    }

    @GetMapping("/register")
    public String showProductForm(Model model) {
        System.out.println("[ProductController] showProductForm()");
        model.addAttribute("product", new Product("", 0, ""));
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

    @PutMapping("/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product, BindingResult bindingResult, Model model) {
        System.out.println("[ProductController] updateProduct()");
        if (bindingResult.hasErrors()) {
            model.addAttribute("product", product);
            return "product-form";
        }
        productService.updateProduct(id, product);
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
    public String searchProduct(@RequestParam("keyword") String keyword, Model model, Pageable pageable) {
        System.out.println("[ProductController] searchProduct()");
        model.addAttribute("searchResults", productService.searchProducts(keyword, pageable));
        model.addAttribute("keyword", keyword);
        return "product-search-list";
    }
}
