package gift.Repository;

import gift.DTO.WishListEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListDao extends JpaRepository<WishListEntity, Long> {


}
