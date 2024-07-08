package gift.product.dao;

import gift.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p " +
            "FROM Product p " +
            "WHERE p.id IN (:productIds)")
    List<Product> findByIds(@Param("productIds") List<Long> productIds);

}
