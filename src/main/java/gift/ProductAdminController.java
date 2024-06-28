package gift;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
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

  @PostMapping("/add")
  public String addProduct(@ModelAttribute Product product) {
    productDao.insertProduct(product);
    return "redirect:/products";
  }

  @GetMapping("/edit/{id}")
  public String editProductForm(@PathVariable Long id, Model model) {
    Product product = productDao.selectProduct(id);
    if (product != null) {
      model.addAttribute("product", product);
      return "product-form";
    }
    return "redirect:/products";
  }

  @PostMapping("/edit/{id}")
  public String updateProduct(@PathVariable Long id, @ModelAttribute Product product) {
    product.setId(id); // Ensure the ID is set to the existing product ID
    productDao.updateProduct(product);
    return "redirect:/products";
  }

  @PostMapping("/delete/{id}")
  public String deleteProduct(@PathVariable Long id) {
    productDao.deleteProduct(id);
    return "redirect:/products";
  }
}
