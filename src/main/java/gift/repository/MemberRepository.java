package gift.repository;

import gift.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {

    public Member findByEmailAndPassword(String email, String password);

}
