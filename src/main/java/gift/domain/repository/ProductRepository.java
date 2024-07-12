package gift.domain.repository;

import gift.domain.model.entity.Product;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByName(String name);

    Optional<Product> findByName(String name);

    boolean existsByIdLessThan(Long nextCursor);

    List<Product> findByOrderByIdDesc(PageRequest of);

    List<Product> findByIdLessThanOrderByIdDesc(Long cursor, PageRequest of);
}