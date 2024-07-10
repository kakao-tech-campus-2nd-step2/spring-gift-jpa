package gift.member.repository;

import gift.global.MyCrudRepository;
import gift.member.domain.Email;
import gift.member.domain.Member;
import gift.member.domain.Password;

import java.util.Optional;

public interface MemberRepository extends MyCrudRepository<Member, Long> {
    Optional<Member> findByEmailAndPassword(Email email, Password password);
}
