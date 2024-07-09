package gift.controller;

import gift.dto.ProductRequest;
import gift.entity.Product;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getAllProducts(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/{id}/edit")
    public String getProduct(@PathVariable long id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        model.addAttribute("productRequest", new ProductRequest(product.getName(), product.getPrice(), product.getImageUrl()));
        return "editForm";
    }

    @GetMapping("/new")
    public String addProductForm(Model model) {
        model.addAttribute("productRequest", new ProductRequest("", 0, ""));
        return "addForm";
    }

    @PostMapping
    public String addProduct(@Valid @ModelAttribute ProductRequest productRequest, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "addForm";
        }
        try {
            productService.save(productRequest);
        } catch (IllegalArgumentException e) {
            bindingResult.addError(new FieldError("productRequest", "name", e.getMessage()));
            return "addForm";
        }
        return "redirect:/api/products";
    }

    @PostMapping("/{id}")
    public String updateProduct(@PathVariable Long id, @Valid @ModelAttribute ProductRequest productRequest, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("product", new Product(id, productRequest.name(), productRequest.price(), productRequest.imageUrl()));
            return "editForm";
        }
        try {
            productService.update(id, productRequest);
        } catch (IllegalArgumentException e) {
            bindingResult.addError(new FieldError("productRequest", "name", e.getMessage()));
            model.addAttribute("product", new Product(id, productRequest.name(), productRequest.price(), productRequest.imageUrl()));
            return "editForm";
        }
        return "redirect:/api/products";
    }

    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/api/products";
    }

    @PostMapping("/delete-batch")
    @ResponseBody
    public String deleteBatch(@RequestBody Map<String, List<Long>> request) {
        productService.deleteBatch(request.get("ids"));
        return "Success";
    }
}
