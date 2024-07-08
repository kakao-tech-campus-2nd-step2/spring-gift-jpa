package gift.repository;

import gift.entity.Product;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findById(long id);
    List<Product> findAll();
    Boolean deleteById(long id);
    Boolean updateById(long id, @Valid Product product);
    Boolean save(@Valid Product product);

}
