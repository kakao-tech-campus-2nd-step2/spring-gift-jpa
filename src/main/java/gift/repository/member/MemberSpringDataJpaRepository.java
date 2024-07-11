package gift.repository.member;

import gift.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberSpringDataJpaRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}