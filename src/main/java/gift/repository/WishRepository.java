package gift.repository;

import gift.entity.Member;
import gift.entity.Product;
import gift.entity.Wish;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishRepository extends JpaRepository<Wish, Long> {
  List<Wish> findByMember(Member member);

  void deleteByMemberAndProduct(Member member, Product product);

  Optional<Wish> findByMemberAndProduct(Member member, Product product);


  List<Wish> findByProductId(Long productId);
}
