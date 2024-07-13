package gift.repository;

import gift.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    /*
     * User 정보를 받아 DB에 저장
     */
    User save(User user);
    /*
     * DB에 저장된 모든 User 정보를 가져와 반환
     */
    Page<User> findAll(Pageable pageable);
    /*
     * User 정보를 id를 기준으로 DB에서 찾아와 반환
     */
    Optional<User> findById(Long id);
    /*
     * User 정보를 id를 기준으로 DB에서 찾아와 반환
     */
    Optional<User> findById(Long id);
    /*
     * User 정보를 userId를 기준으로 DB에서 찾아와 반환
     */
    User findByUserId(String userId);
    /*
     * User 정보를 id를 기준으로 DB에서 삭제
     */
    void deleteById(Long id);
    /*
     * User 정보가 DB에 존재하는지 userId를 통해 검증
     */
    boolean existsByUserId(String userId);
    /*
     * User 정보가 DB에 존재하는지 id를 통해 검증
     */
    boolean existsById(Long id);
    /*
     * User 정보가 DB에 존재하는지 userId와 password를 통해 검증
     */
    boolean existsByUserIdAndPassword(String userId, String password);
}
