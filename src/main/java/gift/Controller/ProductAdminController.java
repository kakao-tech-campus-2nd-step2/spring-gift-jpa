package gift.Controller;

import gift.DTO.ProductDTO;
import gift.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("admin/products")
public class ProductAdminController {

  private final ProductService productService;

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
    model.addAttribute("product", new ProductDTO());
    return "product-form";
  }

  @PostMapping("/add")
  public String addProduct(@Valid @ModelAttribute ProductDTO productDTO){
    productService.addProduct(productDTO);
    return "redirect:/admin/products";
  }

  @GetMapping("product/{id}")
  public String editProductForm(@PathVariable Long id, Model model) {
    ProductDTO productDTO = productService.getProductById(id);
    if (productDTO != null) {
      model.addAttribute("product", productDTO);
      return "product-form";
    }
    return "redirect:/admin/products";
  }

  @PostMapping("product/{id}")
  public String updateProduct(@PathVariable Long id,@Valid @ModelAttribute ProductDTO productDTO)  {
    productService.updateProduct(id, productDTO);
    return "redirect:/admin/products";
  }

  @PostMapping("/{id}")
  public String deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id);
    return "redirect:/admin/products";
  }
}
