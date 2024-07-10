package gift.Repository;

import gift.Entity.WishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends JpaRepository<WishEntity, Long> {
    // 필요한 경우 커스텀 쿼리 메서드 추가 가능
}
