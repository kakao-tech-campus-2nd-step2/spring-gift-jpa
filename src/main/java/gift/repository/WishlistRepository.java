package gift.repository;

import gift.model.Member;
import gift.model.Product;
import gift.model.Wishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    Page<Wishlist> findByMember(Member member, Pageable pageable);

    void deleteByMemberAndProduct(Member member, Product product);
}