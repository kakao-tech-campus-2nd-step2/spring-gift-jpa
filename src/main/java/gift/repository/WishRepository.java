package gift.repository;

import gift.entity.MemberEntity;
import gift.entity.WishEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishRepository extends JpaRepository<WishEntity, Long> {

    Page<WishEntity> findAllByMemberEntity(MemberEntity memberEntity, Pageable pageable);
}

