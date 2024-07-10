package gift.repositories;

import gift.domain.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String email);

}
