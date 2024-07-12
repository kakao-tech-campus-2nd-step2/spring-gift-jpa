package gift.repository;

import gift.entity.Wish;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishRepository extends JpaRepository<Wish, Long> {
  List<Wish> findByMemberEmail(String memberEmail);
  void deleteByMemberEmailAndProductId(String memberEmail, Long productId);
  Optional<Wish> findByMemberEmailAndProductId(String memberEmail, Long productId);
}
