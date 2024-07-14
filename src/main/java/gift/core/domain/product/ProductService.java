package gift.core.domain.product;

import gift.core.PagedDto;
import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.util.List;

public interface ProductService {

    Product get(Long id);

    boolean exists(Long id);

    void createProduct(@Nonnull Product product);

    void updateProduct(@Nonnull Product product);

    List<Product> findAll();

    PagedDto<Product> findAll(Pageable pageable);

    void remove(Long id);

}
