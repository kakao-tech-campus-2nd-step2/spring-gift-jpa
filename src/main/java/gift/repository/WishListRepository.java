package gift.repository;

import gift.domain.WishList.WishList;
import gift.domain.member.Member;
import gift.domain.product.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList, Long> {

    List<WishList> findByMember(Member member);

    Page<WishList> findByMember(Member member, Pageable pageable);

    Optional<WishList> findByMemberAndProduct(Member Member, Product Product);
}
