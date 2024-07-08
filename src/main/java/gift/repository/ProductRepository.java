package gift.repository;

import gift.domain.Product;
import java.util.List;

public interface ProductRepository {
    Product addProduct(Product product);
    List<Product> findAll();
    Product findById(Long id);
    Product updateProduct(Long id, Product updateProduct);
    void deleteProduct(Long id);
}
