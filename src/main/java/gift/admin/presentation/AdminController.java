package gift.admin.presentation;

import gift.product.application.dto.request.ProductRequest;
import gift.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ProductService productService;

    @Autowired
    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public String getListProducts(Pageable pageable, Model model) {
        var products = productService.getProducts(pageable);

        model.addAttribute("products", products.products());
        return "admin/list";
    }

    @GetMapping("/products/{id}")
    public String showEditProductForm(@PathVariable("id") Long id, Model model) {
        var product = productService.getProductDetails(id);

        model.addAttribute("product", product);

        return "admin/edit";
    }

    @PostMapping("/products")
    public String saveProduct(@RequestBody ProductRequest newProduct) {
        productService.saveProduct(newProduct.toProductParam());

        return "admin/list";
    }

    @PatchMapping("/products/{id}")
    public String modifyProduct(@PathVariable("id") Long id, @RequestBody ProductRequest modifyProduct) {
        productService.modifyProduct(id, modifyProduct.toProductParam());

        return "admin/list";
    }

    @DeleteMapping("/products/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);

        return "admin/list";
    }

}
