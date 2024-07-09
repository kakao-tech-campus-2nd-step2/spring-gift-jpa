package gift.repository;

import gift.model.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Override
    Optional<Member> findById(Long id);

    Optional<Member> findByEmail(String email);

    @Override
    Member save(Member member);

    void deleteByEmail(String email);
}
