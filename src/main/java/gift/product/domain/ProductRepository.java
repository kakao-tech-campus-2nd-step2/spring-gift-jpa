package gift.product.domain;

import gift.product.application.command.ProductCreateCommand;
import gift.product.application.command.ProductUpdateCommand;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll();

    Optional<Product> findById(Long productId);

    void addProduct(Product product);

    void deleteProduct(Long productId);

    void updateProduct(Product command);
}
