package gift.Repository;

import gift.DTO.WishListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListDao extends JpaRepository<WishListEntity, Long> {

}
