package gift.product.repository;

import gift.product.model.Wish;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<Wish, Long> {

    List<Wish> findAllByMemberId(Long memberId);

    Optional<Wish> findByIdAndMemberId(Long id, Long memberId);

    Page<Wish> findAll(Pageable pageable);
}
