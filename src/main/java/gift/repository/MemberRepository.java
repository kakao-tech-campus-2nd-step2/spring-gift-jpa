package gift.repository;

import gift.model.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);

    @Override
    Optional<Member> findById(Long id);

    Optional<Member> findByEmail(String email);

    @Override
    Object save(Member member);

    void deleteByEmail(String email);
}
