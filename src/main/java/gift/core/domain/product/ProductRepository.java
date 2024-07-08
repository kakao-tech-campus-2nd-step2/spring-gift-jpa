package gift.core.domain.product;

import jakarta.annotation.Nonnull;

import java.util.List;

public interface ProductRepository {

    Product get(Long id);

    boolean exists(Long id);

    void save(@Nonnull Product product);

    int size();

    List<Product> findAll();

    void remove(Long id);

}
