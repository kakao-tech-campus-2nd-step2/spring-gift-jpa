package gift.product.dao;

import gift.product.entity.Product;
import io.hypersistence.utils.spring.repository.HibernateRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends
        HibernateRepository<Product>,
        JpaRepository<Product, Long> {
    @Query("SELECT p " +
            "FROM Product p " +
            "WHERE p.id IN (:productIds)")
    List<Product> findById(@Param("productIds") List<String> productIds);

}
