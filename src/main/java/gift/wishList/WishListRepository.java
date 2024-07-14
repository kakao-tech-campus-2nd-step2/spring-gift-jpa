package gift.wishList;

import gift.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {
    public List<WishList> findByUser(User user);

    public Page<WishList> findByUser(User user, Pageable pageable);
}
