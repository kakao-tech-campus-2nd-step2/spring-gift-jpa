package gift.Repository;

import gift.DTO.WishListDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListDao extends JpaRepository<WishListDto, Long> {

}
