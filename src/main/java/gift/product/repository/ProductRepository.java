package gift.product.repository;

import gift.product.model.Product;
import java.util.List;

public interface ProductRepository {
    public Product save(Product product);

    public List<Product> findAll();

    public Product findById(Long id) throws Exception;

    public void update(Product product);

    public void delete(Long id);
}
