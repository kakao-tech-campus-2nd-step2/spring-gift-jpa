package gift.domain.product;

import java.util.List;

public interface ProductRepository {

    boolean existsByProductName(String name);

    void createProduct(Product product);

    List<Product> getProducts();

    void updateProduct(Long id, Product product);

    void deleteProductsByIds(List<Long> productIds);

    void deleteProduct(Long id);

    List<Product> getProductsByIds(List<Long> productIds);

}
