package gift.ui.admin;

import gift.api.product.Product;
import gift.api.product.ProductRepository;
import gift.api.product.ProductRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductRepository productRepository;

    public AdminController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping()
    public String view(Model model) {
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("productDto", new ProductRequest());
        return "administrator";
    }

    @PostMapping("/add")
    public RedirectView add(@Valid ProductRequest productRequest) {
        productRepository.save(new Product(
            productRequest.getName(), productRequest.getPrice(), productRequest.getImageUrl()));
        return new RedirectView("/api/products");
    }

    @PutMapping("/update/{id}")
    public RedirectView update(@PathVariable("id") long id, @Valid ProductRequest productRequest) {
        productRepository.save(new Product(
            id, productRequest.getName(), productRequest.getPrice(), productRequest.getImageUrl()));
        return new RedirectView("/api/products");
    }

    @DeleteMapping("/delete/{id}")
    public RedirectView delete(@PathVariable("id") long id) {
        productRepository.deleteById(id);
        return new RedirectView("/api/products");
    }
}
