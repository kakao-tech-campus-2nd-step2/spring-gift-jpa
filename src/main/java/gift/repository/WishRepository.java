package gift.repository;

import gift.entity.WishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishRepository extends JpaRepository<WishEntity, Long> {

    public List<WishEntity> findAllByMemberId(Long memberId);
}

