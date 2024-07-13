package gift.wishlist.repository;

import gift.member.entity.MemberEntity;
import gift.wishlist.entity.WishEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends JpaRepository<WishEntity, Long> {

    Page<WishEntity> findAllByMemberEntity(MemberEntity memberEntity, Pageable pageable);
}

