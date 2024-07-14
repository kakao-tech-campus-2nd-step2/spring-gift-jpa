package gift.Repository;

import gift.Model.Member;
import gift.Model.Product;
import gift.Model.ResponseWishDTO;
import gift.Model.Wish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish, Long> {
    List<Wish> findWishListByMember(Member member);

    Optional<Wish> findByMemberAndProduct(Member member, Product product);

    Page<Wish> findByMember(Member member, Pageable pageable);
}
