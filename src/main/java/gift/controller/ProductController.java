package gift.controller;

import gift.exception.InvalidProductException;
import gift.exception.ProductNotFoundException;
import gift.model.Product;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController( ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("product", new Product());
        return "product-list";
    }

    @PostMapping("/add")
        Product addedProduct = productService.addProduct(product);
    }

    //@PostMapping("/{id}")

        productService.updateProduct(id, product);
    }

    @PostMapping("/delete/{id}")
        productService.deleteProduct(id);
    }

    @GetMapping("/view/{id}")
        Optional<Product> product = productService.getProductById(id);
        model.addAttribute("product", product);

    }

    @GetMapping("/{id}")
    @ResponseBody
    public Product getProductById(@PathVariable("id") Long id) {
        try {
            return productService.getProductById(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Product not found: " + e.getMessage());
        }
    }
}