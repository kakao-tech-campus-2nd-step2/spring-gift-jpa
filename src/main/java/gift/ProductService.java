package gift;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ProductService {
  private final Map<Long, Product> products = new HashMap<>();
  private Long nextId = 1L;

  public List<Product> getAllProducts() {
    return new ArrayList<>(products.values());
  }

  public Product getProductById(Long id) {
    return products.get(id);
  }

  public Product addProduct(Product product) {
    product.setId(nextId++);
    products.put(product.getId(), product);
    return product;
  }

  public Product updateProduct(Long id, Product updatedProduct) {
    Product existingProduct = products.get(id);
    if (existingProduct != null) {
      existingProduct.setName(updatedProduct.getName());
      existingProduct.setPrice(updatedProduct.getPrice());
      return existingProduct;
    }
    return null;
  }

  public Product deleteProduct(Long id) {
    return products.remove(id);
  }
}
