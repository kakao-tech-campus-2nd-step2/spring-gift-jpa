package gift.repository;

import gift.dto.ProductDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductOptionRepository {
    @Query("SELECT new com.example.gift.dto.ProductDTO(p.id, p.name, p.price, p.imageUrl, o.id.option) " +
            "FROM Product p JOIN Option o ON p.id = o.id.id")
    List<ProductDTO> findProductWithOptions();
}
