package gift.repository;

import gift.model.Wish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishRepository extends JpaRepository<Wish, Long> {

    Page<Wish> findAllByMemberId(Long memberId, Pageable pageable);

    Wish findByMemberIdAndProductId(Long memberId, Long ProductId);

    void deleteByMemberIdAndProductId(Long memberId, Long ProductId);
}
