package gift.wishlist.repository;

import gift.product.entity.Product;
import gift.wishlist.entity.WishList;
import gift.wishlist.model.WishListId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList, WishListId> {
}
