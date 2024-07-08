package gift.repository;


import gift.entity.Wish;
import org.springframework.data.jpa.repository.JpaRepository;

interface WishRepository extends JpaRepository<Wish,Long> {

    public Wish findByMemberId(Long memberId);

    public void deleteByMemberIdAndProductId(Long memberId, Long productId);
}
