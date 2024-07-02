package gift;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("admin/products")
public class ProductAdminController {

  private final ProductDao productDao;

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
  public String addProduct(@Valid @ModelAttribute Product product) throws MethodArgumentNotValidException{
    productDao.insertProduct(product);
    return "redirect:/admin/products";
  }

  @GetMapping("product/{id}")
  public String editProductForm(@PathVariable Long id, Model model) {
    Product product = productDao.selectProduct(id);
    if (product != null) {
      model.addAttribute("product", product);
      return "product-form";
    }
    return "redirect:/admin/products";
  }

  @PostMapping("product/{id}")
  public String updateProduct(@PathVariable Long id,@Valid @ModelAttribute Product product) throws MethodArgumentNotValidException {
    product.setId(id);
    productDao.updateProduct(product);
    return "redirect:/admin/products";
  }

  @PostMapping("/{id}")
  public String deleteProduct(@PathVariable Long id) {
    productDao.deleteProduct(id);
    return "redirect:/admin/products";
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex){
    String message="유효성 검사 실패: " + ex.getBindingResult().getFieldError().getDefaultMessage();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
  }
}
