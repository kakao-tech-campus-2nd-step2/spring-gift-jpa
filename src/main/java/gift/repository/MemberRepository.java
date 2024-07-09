package gift.repository;

import gift.entityForJpa.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);

    Integer countByEmail(String email);
}
