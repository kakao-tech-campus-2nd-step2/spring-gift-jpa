package gift.repository;

import gift.entity.WishListEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishListRepository extends JpaRepository<WishListEntity, Long> {

    List<WishListEntity> findByMemberId(Long memberId);

}
