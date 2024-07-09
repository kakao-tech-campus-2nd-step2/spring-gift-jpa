package gift.Controller;

import gift.Entity.ProductEntity;
import gift.DAO.ProductRepository;
import org.springframework.stereotype.Controller;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductViewController {
    private final ProductRepository productRepository;

    public ProductViewController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "product-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new ProductEntity(1L, "Product1", 100.0));
        return "product-form";
    }

    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute ProductEntity product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "product-form";
        }

        productRepository.save(product);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        ProductEntity product = productRepository.findById(id);
        model.addAttribute("product", product);
        return "product-form";
    }

    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable("id") Long id, @Valid @ModelAttribute ProductEntity updatedProduct, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "product-form";
        }
        // 기존 제품을 조회
        ProductEntity product = productRepository.findById(id);
        if (!product.getId().equals(updatedProduct.getId())) {
            // ID변경이 잘 안 돼서 추가한 코드.
            productRepository.deleteById(id);
        }

        productRepository.save(updatedProduct);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productRepository.deleteById(id);
        return "redirect:/products";
    }
}
