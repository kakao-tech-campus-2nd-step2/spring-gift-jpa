package gift.controller;

import gift.model.ProductDTO;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/product")
public class AdminController {

    private final ProductService productService;

    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/list")
    public String productList(Model model) {
        List<ProductDTO> products = productService.getAllProduct();
        model.addAttribute("products", products);
        return "product-list";
    }

    @GetMapping("/add")
    public ModelAndView showAddPage() {
        ModelAndView modelAndView = new ModelAndView("product-add");
        modelAndView.addObject("product", new ProductDTO(0L, "", 0L, ""));
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditPage(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("product-edit");
        ProductDTO productDTO = productService.getProductById(id);
        modelAndView.addObject("product", productDTO);
        return modelAndView;
    }

    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute ProductDTO productDTO) {
        productService.createProduct(productDTO);
        return "redirect:/admin/product/list";
    }

    @PutMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id,
        @Valid @ModelAttribute ProductDTO productDTO) {
        productService.updateProduct(id, productDTO);
        return "redirect:/admin/product/list";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }
}
