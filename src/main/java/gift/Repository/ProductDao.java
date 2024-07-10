package gift.Repository;

import gift.DTO.ProductEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<ProductEntity, Long> {

  ProductEntity save(ProductEntity entity);

  List<ProductEntity> findAll();

  Optional<ProductEntity> findById(Long Id);

  void deleteById(Long Id);

}

