package gift.repository;

import gift.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public  interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllById(Iterable<Long> productsId);
}
