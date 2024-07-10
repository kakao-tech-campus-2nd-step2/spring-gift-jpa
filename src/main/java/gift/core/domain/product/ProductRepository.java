package gift.core.domain.product;

import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findById(Long id);

    boolean exists(Long id);

    void save(@Nonnull Product product);

    long size();

    List<Product> findAll();

    void remove(Long id);

}
