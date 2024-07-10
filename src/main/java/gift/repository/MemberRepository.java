package gift.repository;

import gift.model.Member;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends BaseRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);
}
