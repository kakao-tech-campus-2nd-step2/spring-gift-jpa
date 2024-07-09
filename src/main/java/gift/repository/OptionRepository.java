package gift.repository;

import gift.compositeKey.OptionId;
import gift.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OptionRepository extends JpaRepository<Option, OptionId> {
    @Query("delete from Option u where u.id = :product_id")
    void deleteByProductID(@Param("product_id")int id);
}
