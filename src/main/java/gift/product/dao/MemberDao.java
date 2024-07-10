package gift.product.dao;

import gift.product.model.Member;

import java.util.Collection;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberDao extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Long findIdByEmail(String email);
    Member findMEmberByEmailLike(String email);
}
