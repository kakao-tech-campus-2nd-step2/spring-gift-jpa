package gift.domain.repository;

import gift.domain.model.entity.Product;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByName(String name);

    Optional<Product> findByName(String name);

    @Query("SELECT p FROM Product p ORDER BY " +
        "CASE WHEN :sort = 'id' THEN p.id " +
        "     WHEN :sort = 'price' THEN p.price " +
        "     WHEN :sort = 'name' THEN p.name " +
        "     ELSE p.id END ASC")
    Page<Product> findAllWithSortAsc(@Param("sort") String sort, Pageable pageable);

    @Query("SELECT p FROM Product p ORDER BY " +
        "CASE WHEN :sort = 'id' THEN p.id " +
        "     WHEN :sort = 'price' THEN p.price " +
        "     WHEN :sort = 'name' THEN p.name " +
        "     ELSE p.id END DESC")
    Page<Product> findAllWithSortDesc(@Param("sort") String sort, Pageable pageable);
}