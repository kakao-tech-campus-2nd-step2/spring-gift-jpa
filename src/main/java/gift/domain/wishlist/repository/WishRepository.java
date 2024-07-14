package gift.domain.wishlist.repository;

import gift.domain.member.entity.Member;
import gift.domain.wishlist.entity.Wish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends JpaRepository<Wish, Long> {

    Page<Wish> findAllByMember(Member member, Pageable pageable);
}

