package gift.repository;

import gift.model.Member;
import gift.model.Product;
import gift.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findByMember(Member member);

    void deleteByMemberAndProduct(Member member, Product product);
}