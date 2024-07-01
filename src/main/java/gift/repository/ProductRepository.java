package gift.repository;

import gift.domain.Product;

import java.util.List;

public interface ProductRepository {
    Product getProductById(Long id);

    Long saveProduct(Product product);

    Long updateProduct(Long id, Product product);

    Long deleteProductById(Long id);

    List<Product> getAllProducts();

    void deleteProductByIdList(List<Long> productIds);
}
