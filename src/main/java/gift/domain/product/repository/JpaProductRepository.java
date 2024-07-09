package gift.domain.product.repository;

import gift.domain.product.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface JpaProductRepository extends JpaRepository<Product, Long> {

    boolean existsByName(String name);

    @Transactional
    @Modifying
    @Query("DELETE FROM Product p WHERE p.id IN :productIds")
    void deleteByIds(@Param("productIds") List<Long> productIds);

    Product findByName(String name);
}
