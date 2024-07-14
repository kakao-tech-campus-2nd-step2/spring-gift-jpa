package gift.product;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/manager")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        ;
        this.productService = productService;
    }

    @PostMapping("/products/add")
    public String addProduct(@Valid @ModelAttribute("newProduct") ProductDTO newProduct, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "AddProduct";
        }

        Product product = productService.insertNewProduct(newProduct);
        redirectAttributes.addAttribute("id", product.getId());

        return "redirect:/manager/products/{id}";
    }

    @PutMapping("/products/update/{id}")
    @Transactional
    public String updateProduct(@PathVariable Long id, @Valid @ModelAttribute("product") ProductDTO product, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "UpdateProduct";
        }
        productService.updateProduct(id, product);

        redirectAttributes.addAttribute("id", id);
        return "redirect:/manager/products/{id}";
    }

    @DeleteMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/manager/products";
    }

    @GetMapping("/products/pages")
    public ResponseEntity<Page<Product>> getProductsPage(@RequestParam(required = false, defaultValue = "0", value = "page") int page,
                                                         @RequestParam(required = false, defaultValue = "10", value = "size") @Min(1) @Max(20) int size,
                                                         @RequestParam(required = false, defaultValue = "id", value = "sortBy") String sortBy,
                                                         @RequestParam(required = false, defaultValue = "asc", value = "sortDirection") String sortDirection) {

        return ResponseEntity.ok(productService.getProductPages(page, size, sortBy, sortDirection));

    }

    @GetMapping("/products")
    public String getProductsView(Model model) {
        model.addAttribute("products", productService.findAllProducts());
        return "ManageProduct";
    }

    @GetMapping("/products/add")
    public String addProductView(Model model) {
        model.addAttribute("newProduct", new ProductDTO());
        return "AddProduct";
    }

    @GetMapping("/products/update/{id}")
    public String updateProduct(@PathVariable Long id, Model model) {
        model.addAttribute(productService.findByID(id));
        return "UpdateProduct";
    }

    @GetMapping("/products/{id}")
    public String getProduct(@PathVariable long id, Model model) {
        model.addAttribute(productService.findByID(id));
        return "ProductInfo";
    }
}
