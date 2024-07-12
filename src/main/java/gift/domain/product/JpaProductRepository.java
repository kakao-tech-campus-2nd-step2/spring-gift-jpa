package gift.domain.product;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface JpaProductRepository extends JpaRepository<Product, Long> {

    boolean existsByName(String name);

    @Transactional
    void deleteAllByIdIn(List<Long> productIds);

    List<Product> findAll();

    // paging
    Page<Product> findAll(Pageable pageable);

    Page<Product> findAllByIdIn(List<Long> cartItemIds, Pageable pageable);
}
