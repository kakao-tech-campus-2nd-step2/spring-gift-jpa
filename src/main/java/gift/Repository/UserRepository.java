package gift.Repository;

import gift.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // 필요한 경우 커스텀 쿼리 메서드 추가 가능
}
