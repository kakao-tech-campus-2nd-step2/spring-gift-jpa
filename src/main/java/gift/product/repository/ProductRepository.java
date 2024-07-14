package gift.product.repository;

import gift.global.MyCrudRepository;
import gift.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepository extends MyCrudRepository<Product, Long> {
    Page<Product> findAll(Pageable pageable);
}
