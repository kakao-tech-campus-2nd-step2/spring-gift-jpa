package gift.admin.presentation;

import gift.product.application.dto.request.ProductRequest;
import gift.product.domain.Product;
import gift.product.exception.ProductNotFoundException;
import gift.product.persistence.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final ProductRepository productRepository;

    @Autowired
    public AdminController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping()
    public String getListProducts(Model model) {
        List<Product> products = productRepository.findAll().stream().toList();
        model.addAttribute("products", products);
        return "admin/list";
    }

    @GetMapping("/products/{id}")
    public String showEditProductForm(@PathVariable("id") Long id, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> ProductNotFoundException.of(id));

        model.addAttribute("product", product);

        return "admin/edit";
    }

    @PostMapping("/products")
    public String saveProduct(@RequestBody ProductRequest newProduct) {
        productRepository.save(null);

        return "admin/list";
    }

    @PatchMapping("/products/{id}")
    public String modifyProduct(@PathVariable("id") Long id, @RequestBody ProductRequest modifyProduct) {
        Product findedProduct = productRepository.findById(id)
                .orElseThrow(() -> ProductNotFoundException.of(id));

        productRepository.save(modifyProduct.toModel(id));

        return "admin/list";
    }

    @DeleteMapping("/products/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        Product findedProduct = productRepository.findById(id)
                .orElseThrow(() -> ProductNotFoundException.of(id));

        productRepository.delete(findedProduct);

        return "admin/list";
    }

}
