package gift;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductAdminController {

  private final ProductService productService;

  @Autowired
  public ProductAdminController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public String listProducts(Model model) {
    model.addAttribute("products", productService.getAllProducts());
    return "product-list";
  }

  @GetMapping("/new")
  public String newProductForm(Model model) {
    model.addAttribute("product", new Product());
    return "product-form";
  }

  @PostMapping("/add")
  public String addProduct(@ModelAttribute Product product) {
    productService.addProduct(product);
    return "redirect:/products";
  }

  @GetMapping("/edit/{id}")
  public String editProductForm(@PathVariable Long id, Model model) {
    Product product = productService.getProductById(id);
    if (product != null) {
      model.addAttribute("product", product);
      return "product-form";
    } else {
      return "redirect:/products";
    }
  }

  @PostMapping("/edit/{id}")
  public String updateProduct(@PathVariable Long id, @ModelAttribute Product product) {
    productService.updateProduct(id, product);
    return "redirect:/products";
  }

  @PostMapping("/delete/{id}")
  public String deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id);
    return "redirect:/products";
  }
}
