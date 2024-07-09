package gift.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByMember_id(int member_id);
    int searchNumByMember_idAndProduct_id(int member_id, int product_id);
    void deleteByMember_idAndMember_id(int member_id, int product_id);
    Wishlist save(Wishlist wishlist);

}