package gift.repository;

import gift.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member save(Member member);

    Optional<Member> findByEmail(String email);

    boolean existsById(Long id);

    boolean existsByEmail(String email);

    void deleteById(Long id);
}
