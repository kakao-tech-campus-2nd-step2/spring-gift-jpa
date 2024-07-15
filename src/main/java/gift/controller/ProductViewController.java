package gift.controller;

import gift.model.Product;
import gift.model.ProductDto;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping("/products")
public class ProductViewController {
  private final ProductService productService;

  public ProductViewController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public String listProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, Model model) {
    Pageable pageable = PageRequest.of(page, size);
    Page<ProductDto> productPage = productService.findAll(pageable);
    model.addAttribute("products", productPage.getContent());
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", productPage.getTotalPages());
    return "product-list";
  }

  @GetMapping("/new")
  public String showCreateForm(Model model) {
    model.addAttribute("product", new Product());
    return "product-form";
  }

  @PostMapping
  public String createProduct(@Valid @ModelAttribute("product") ProductDto product, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      return "product-form";
    }
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
    ProductDto product = productService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
    model.addAttribute("product", product);
    return "product";
  }

  @PostMapping("/{id}")
  public String updateProduct(@PathVariable Long id, @Valid @ModelAttribute ProductDto productDetails, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      return "product-form";
    }
    boolean isUpdated = productService.updateProduct(id, productDetails);
    if (isUpdated) {
      model.addAttribute("message", "Product updated successfully.");
    } else {
      model.addAttribute("error", "Product update failed.");
    }
    return "redirect:/products";
  }

  @GetMapping("/delete/{id}")
  public String deleteProduct(@PathVariable Long id) {
    productService.deleteById(id);
    return "redirect:/products";
  }
}
