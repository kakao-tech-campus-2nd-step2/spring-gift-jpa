package gift.model.product;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

public interface ProductDao {

    void insert(Product product);

    Optional<Product> findById(Long id);

    List<Product> findAll();

    void deleteById(Long id);

    void update(Product product);

    List<Product> findPaging(int page, int size);

    Long count();
}
