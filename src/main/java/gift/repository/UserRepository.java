package gift.repository;

import gift.entity.UserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
//    유저 리스트 조회(isDelete가 1이면 삭제됨)
    List<UserEntity> findAllByIsDelete(Integer isDelete);
//    id로 유저조회(isDelete가 1이면 삭제됨)
    Optional<UserEntity> findByIdAndIsDelete(Long id, Integer isDelete);
//    이메일 중복 여부 검증
    Optional<UserEntity> findByEmailAndIsDelete(String email, Integer isDelete);
//    이메일,pw검증
    Optional<UserEntity>findAllByEmailAndPasswordAndIsDelete(String email, String password, Integer isDelete);
}
