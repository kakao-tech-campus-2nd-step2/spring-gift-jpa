package gift.repository;

import gift.compositeKey.OptionId;
import gift.dto.ProductDTO;
import gift.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option, OptionId> {
    @Query("delete from Option u where u.id = :product_id")
    void deleteByProductID(@Param("product_id")int id);

    @Query("SELECT p.id, p.name, p.price, p.imageUrl, o.id.option FROM Product p join Option o ON p.id = o.id.id")
    List<Object[]> findAllWithOption();
}
