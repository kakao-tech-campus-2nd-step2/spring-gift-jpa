package gift.product.repository;

import gift.product.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    public Product save(Product product);

    public List<Product> findAll();

    public Optional<Product> findById(Long id);

    public void deleteById(Long id);
}
