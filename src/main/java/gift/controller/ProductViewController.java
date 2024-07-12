package gift.controller;

import gift.model.Product;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products")
public class ProductViewController {
  private final ProductService productService;

  public ProductViewController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public String listProducts(Model model) {
    model.addAttribute("products", productService.findAll());
    return "product-list";
  }

  @GetMapping("/new")
  public String showCreateForm(Model model) {
    model.addAttribute("product", new Product());
    return "product-form";
  }

  @PostMapping
  public String createProduct(@Valid @ModelAttribute Product product, RedirectAttributes redirectAttributes) {
    productService.save(product);
    return "redirect:/products";
  }

  @GetMapping("/edit/{id}")
  public String showEditForm(@PathVariable Long id, Model model) {
    model.addAttribute("product", productService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id)));
    return "product-form";
  }

  @GetMapping("/{id}")
  public String getProduct(@PathVariable Long id, Model model) {
    Product product = productService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
    model.addAttribute("product", product);
    return "product";
  }

  @PostMapping("/{id}")
  public String updateProduct(@PathVariable Long id, @Valid @ModelAttribute Product productDetails, RedirectAttributes redirectAttributes) {
    boolean isUpdated = productService.updateProduct(id, productDetails);
    if (isUpdated) {
      redirectAttributes.addFlashAttribute("message", "Product updated successfully.");
    } else {
      redirectAttributes.addFlashAttribute("error", "Product update failed.");
    }
    return "redirect:/products";
  }

  @GetMapping("/delete/{id}")
  public String deleteProduct(@PathVariable Long id) {
    productService.deleteById(id);
    return "redirect:/products";
  }
}