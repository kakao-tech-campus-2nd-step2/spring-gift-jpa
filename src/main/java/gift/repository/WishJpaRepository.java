package gift.repository;

import gift.model.member.Member;
import gift.model.product.Product;
import gift.model.wish.Wish;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishJpaRepository extends JpaRepository<Wish, Long> {

    Optional<Wish> findByMemberAndProduct(Member member, Product product);

    List<Wish> findByMemberId(Long memberId);
}
