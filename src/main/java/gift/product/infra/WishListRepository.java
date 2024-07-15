package gift.product.infra;

import gift.product.domain.WishList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {

    WishList findByUserId(Long userId);


    Page<WishList> findByUserId(Long userId, Pageable pageable);

}
