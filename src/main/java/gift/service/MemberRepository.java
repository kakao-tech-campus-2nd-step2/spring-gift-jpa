package gift.service;

import gift.model.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmailAndPassword(String email, String password);
}