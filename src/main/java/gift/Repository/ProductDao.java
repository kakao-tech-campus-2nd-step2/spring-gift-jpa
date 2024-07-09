package gift.Repository;

import gift.DTO.ProductDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<ProductDto, Long> {

  ProductDto save(ProductDto entity);

  List<ProductDto> findAll();

  Optional<ProductDto> findById(Long Id);

  void deleteById(Long Id);

}