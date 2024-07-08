package gift.repository;

import gift.model.Product;
import gift.model.ProductDTO;

import java.util.List;

public interface ProductRepository {

    Product save(ProductDTO form);

    boolean delete(Long id);

    Product edit(Long id, ProductDTO form);

    Product findById(Long id);

    List<Product> findAll();
}
