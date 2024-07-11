package gift.repository;

import gift.entity.Member;
import java.util.Optional;
import javax.swing.text.html.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmailAndPassword(String email, String password);
    Optional<Long> findIdByEmail(String email);
    Optional<Member>findByEmail(String email);
}
