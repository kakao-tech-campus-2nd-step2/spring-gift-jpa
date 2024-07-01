package gift;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("admin/products")
public class ProductAdminController {

  private final ProductDao productDao;

  @Autowired
  public ProductAdminController(ProductDao productDao) {
    this.productDao = productDao;
  }

  @GetMapping
  public String listProducts(Model model) {
    model.addAttribute("products", productDao.selectAllProducts());
    return "product-list";
  }

  @GetMapping("/new")
  public String newProductForm(Model model) {
    model.addAttribute("product", new Product());
    return "product-form";
  }

  @PostMapping
  public String addProduct(@ModelAttribute Product product) {
    productDao.insertProduct(product);
    return "redirect:/products";
  }

  @GetMapping("product/{id}")
  public String editProductForm(@PathVariable Long id, Model model) {
    Product product = productDao.selectProduct(id);
    if (product != null) {
      model.addAttribute("product", product);
      return "product-form";
    }
    return "redirect:/products";
  }

  @PostMapping("product/{id}")
  public String updateProduct(@PathVariable Long id, @ModelAttribute Product product) {
    product.setId(id);
    productDao.updateProduct(product);
    return "redirect:/products";
  }

  @PostMapping("/{id}")
  public String deleteProduct(@PathVariable Long id) {
    productDao.deleteProduct(id);
    return "redirect:/products";
  }
}
