package gift.repository;

import gift.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    /*
     * User 정보를 받아 DB에 저장
     */
    User save(User user);
    /*
     * DB에 저장된 모든 User 정보를 가져와 반환
     */
    List<User> findAll();
    /*
     * User 정보를 userId를 기준으로 DB에서 찾아와 반환
     */
    User findByUserId(String userId);
    /*
     * User 정보를 userId를 기준으로 DB에서 삭제
     */
    void deleteByUserId(String userId);
}
