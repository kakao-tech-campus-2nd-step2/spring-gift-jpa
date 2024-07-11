package gift.repository;

import gift.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
