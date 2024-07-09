package gift.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByMember_id(int member_id);
    int searchNumbyMember_idAndProduct_id(int member_id, int product_id);
    void deleteByMember_idAndMember_id(int member_id, int product_id);
    String save(String customer_id, String product_id, int num);

}