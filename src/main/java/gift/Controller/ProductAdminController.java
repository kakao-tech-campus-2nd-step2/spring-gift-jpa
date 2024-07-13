package gift.Controller;

import gift.DTO.Product;
import gift.DTO.ProductDto;
import gift.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/products")
public class ProductAdminController {

  private final ProductService productService;

  public ProductAdminController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public String listProducts(Model model, Pageable pageable) {
    model.addAttribute("products", productService.getAllProducts(pageable));
    return "product-list";
  }

  @GetMapping("/new")
  public String newProductForm(Model model) {
    model.addAttribute("product", new Product());
    return "product-form";
  }

  @PostMapping("/add")
  public String addProduct(@Valid @ModelAttribute ProductDto productDto) {
    productService.addProduct(productDto);
    return "redirect:/admin/products";
  }

  @GetMapping("product/{id}")
  public String editProductForm(@PathVariable Long id, Model model) {
    ProductDto product = productService.getProductById(id);
    model.addAttribute("product", product);
    return "product-form";
  }

  @PostMapping("product/{id}")
  public String updateProduct(@PathVariable Long id, @Valid @ModelAttribute ProductDto productDto) {
    productService.updateProduct(id, productDto);
    return "redirect:/admin/products";
  }

  @PostMapping("/{id}")
  public String deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id);
    return "redirect:/admin/products";
  }
}
