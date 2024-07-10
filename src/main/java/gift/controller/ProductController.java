package gift.controller;

import gift.dto.ProductDTO;
import gift.model.Product;
import gift.model.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/api/products")
public class ProductController {
    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public String getAllProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "index";
    }

    @GetMapping("/add")
    public String addProductForm(Model model) {
        model.addAttribute("product", new @Valid Product());
        return "addProduct";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute @Valid ProductDTO productDTO) {
        Product savedProduct = productRepository.save(productDTO);
        productRepository.validateKaKaoName(savedProduct.getName());
        return "redirect:/api/products";
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 상품입니다."));
        model.addAttribute("product", product);
        return "editProduct";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute @Valid ProductDTO productDTO) {
        productRepository.update(id, productDTO);
        productRepository.validateKaKaoName(productDTO.getName());
        return "redirect:/api/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return "redirect:/api/products";
    }
}
