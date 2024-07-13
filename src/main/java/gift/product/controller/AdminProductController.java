package gift.product.controller;

import gift.product.dto.ProductDTO;
import gift.product.service.ProductService;
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
        model.addAttribute("product", new ProductDTO("", 0, ""));
        return "product-form";
    }

    @PostMapping()
    public String registerProduct(@Valid @ModelAttribute ProductDTO productDTO, BindingResult bindingResult, Model model) {
        System.out.println("[ProductController] registerProduct()");
        if (bindingResult.hasErrors()) {
            model.addAttribute("product", productDTO);
            return "product-form";
        }
        productService.registerProduct(productDTO);
        return "redirect:/admin/product/list";
    }

    @GetMapping("/update/{id}")
    public String updateProductForm(@PathVariable Long id, Model model) {
        System.out.println("[ProductController] updateProductForm()");
        ProductDTO productDTO = productService.getDTOById(id);
        model.addAttribute("product", productDTO);
        return "product-update-form";
    }

    @PutMapping("/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute ProductDTO productDTO, BindingResult bindingResult, Model model) {
        System.out.println("[ProductController] updateProduct()");
        if (bindingResult.hasErrors()) {
            model.addAttribute("product", productDTO);
            return "product-form";
        }
        productService.updateProduct(id, productDTO);
        return "redirect:/admin/product/list";
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id, Model model) {
        System.out.println("[ProductController] deleteProduct()");
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
