package gift.core.domain.product;

import gift.core.PagedDto;
import jakarta.annotation.Nonnull;

import java.util.List;

public interface ProductService {

    Product get(Long id);

    boolean exists(Long id);

    void createProduct(@Nonnull Product product);

    void updateProduct(@Nonnull Product product);

    List<Product> findAll();

    PagedDto<Product> findAll(int page, int size);

    void remove(Long id);

}
