package gift.jpaRepo;

import gift.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select Member from Member where email = :email and password = :password")
    Optional<Member> findIdByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    @Query("select Member from Member where email = :email")
    Optional<Member> findByEmail(String email);
}
