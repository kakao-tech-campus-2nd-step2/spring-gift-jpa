package gift.repository;

import gift.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<gift.entity.Member> findByEmail(String email);
}
