package gift.Repository;

import gift.DTO.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListDao extends JpaRepository<WishList, Long> {

}
