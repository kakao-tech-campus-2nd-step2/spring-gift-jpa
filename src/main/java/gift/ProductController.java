package gift;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

  private final ProductDao productDao;

  @Autowired
  public ProductController(ProductDao productDao) {
    this.productDao = productDao;
  }


  @GetMapping
  public List<Product> getAllProducts() {
    List<Product> productDTO = new ArrayList<Product>();
    productDTO=productDao.selectAllProducts();
    return productDTO;
  }

  @GetMapping("/{id}")
  public ResponseEntity<Product> getProductById(@PathVariable Long id) {
    Product product = productDao.selectProduct(id);
    if (product != null) {
      return ResponseEntity.ok(product);
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public Product addProduct(@RequestBody Product product) {
    productDao.insertProduct(product);
    return product;
  }

  @PutMapping("/{id}")
  public ResponseEntity<Product> updateProduct(@PathVariable Long id,
    @RequestBody Product updatedProduct) {
    Product existingProduct = productDao.selectProduct(id);
    if (existingProduct != null) {
      updatedProduct.setId(id);
      productDao.updateProduct(updatedProduct);
      return ResponseEntity.ok(updatedProduct);
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    Product existingProduct = productDao.selectProduct(id);
    if (existingProduct != null) {
      productDao.deleteProduct(id);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }
}
