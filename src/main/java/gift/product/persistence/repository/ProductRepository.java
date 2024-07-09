package gift.product.persistence.repository;

import gift.product.persistence.entity.Product;

import java.util.List;
import java.util.Map;

public interface ProductRepository {

    Product getProductById(Long id);

    Long saveProduct(Product product);

    Long deleteProductById(Long id);

    List<Product> getAllProducts();

    void deleteProductByIdList(List<Long> productIds);

    Map<Long, Product> getProductsByIds(List<Long> productIds);

    void deleteAll();

    Product getReferencedProduct(Long productId);
}
