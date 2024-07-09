package gift.member.repository;

import gift.member.domain.Email;
import gift.member.domain.Member;
import gift.member.domain.Password;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findAll();

    Member save(Member member);

    Optional<Member> findByEmailAndPassword(Email email, Password password);

    Optional<Member> findById(Long id);

    void deleteById(Long id);
}
