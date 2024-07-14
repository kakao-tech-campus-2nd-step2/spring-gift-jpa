package gift.Controller;

import gift.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import gift.Model.Product;
import gift.Model.RequestProduct;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String getProduct(@RequestParam (defaultValue = "0", value="page") int page,
                             @RequestParam (defaultValue = "3", value="size") int size,
                             @RequestParam (defaultValue = "id", value="sortField") String sortField,
                             @RequestParam (defaultValue = "ASC", value="sortDir") String sortDir,
                             Model model) {
        Page<Product> productPage = productService.getAllProducts(page, size, sortField, sortDir);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        return "products";
    }

    @GetMapping("/products/new")
    public String newProductForm(Model model) {
        model.addAttribute("product", new RequestProduct("", 0, ""));
        return "new-product";
    }

    @PostMapping("/products")
    public String newProduct(@Valid @ModelAttribute RequestProduct requestProduct) {
        productService.addProduct(requestProduct);
        return "redirect:/api/products";
    }

    @GetMapping("/products/edit/{id}")
    public String editProductForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.selectProduct(id);
        model.addAttribute("product", new RequestProduct(product.getName(), product.getPrice(), product.getImageUrl()));
        model.addAttribute("id", id);
        return "edit-product";
    }

    @PutMapping("/products/edit/{id}")
    public String updateProduct(@PathVariable("id") Long id, @Valid @ModelAttribute RequestProduct requestProduct) {
        productService.editProduct(id, requestProduct);
        return "redirect:/api/products";
    }

    @DeleteMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/api/products";
    }

}