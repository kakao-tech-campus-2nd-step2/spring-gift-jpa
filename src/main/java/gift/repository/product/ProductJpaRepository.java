package gift.repository.product;

import gift.model.product.Product;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface ProductJpaRepository extends JpaRepository<Product, Long>, ProductRepository {

    Page<Product> findAllByOrderByIdDesc(Pageable pageable);
}
