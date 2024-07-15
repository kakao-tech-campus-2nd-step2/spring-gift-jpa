package gift.database;

import gift.model.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProductRepository extends JpaRepository<Product, Long> {

    @Override
    <S extends Product> S save(S entity);

    @Override
    Optional<Product> findById(Long aLong);

    @Override
    void delete(Product entity);

    @Override
    List<Product> findAll();

    Page<Product> findAll(Pageable pageable);
}
