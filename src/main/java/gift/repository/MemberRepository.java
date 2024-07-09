package gift.repository;

import gift.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member save(Member member);

    Optional<Member> findByEmail(String email);

    boolean existsById(Long id);

    boolean existsByEmail(String email);

    void deleteById(Long id);
}
