package gift.repository;

import gift.model.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();
    Product findById(Long id);
    boolean save(Product product);
    boolean update(Product product);
    boolean delete(Long id);
    List<Product> findPaginated(int page, int size);
}
