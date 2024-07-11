package gift.repository;

import gift.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishListRepository extends JpaRepository<WishList, Long> {
    List<WishList> findByUser_Email(String email); // userEmail에서 user.email로 변경. 언더바가 . 역할을 하나봐요...
    Optional<WishList> findByUser_EmailAndProductId(String email, Long productId);
}
