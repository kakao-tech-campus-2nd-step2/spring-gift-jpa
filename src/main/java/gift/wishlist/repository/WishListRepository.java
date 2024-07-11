package gift.wishlist.repository;

import gift.member.model.Member;
import gift.product.model.Product;
import gift.wishlist.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {
    static void removeProductFromWishList(Long userId, Long productId) {
    }

    List<WishList> findByMember(Member member);

    WishList findByUserId(Long userId);

    void addProductToWishList(Long userId, Product product);
}