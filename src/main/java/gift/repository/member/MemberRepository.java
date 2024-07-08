package gift.repository.member;

import gift.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member memberSave(Member member);

    Optional<Member> findMemberById(Long id);

    Optional<Member> findMemberByEmail(String email);

    Optional<Member> findMemberByEmailAndPassword(String email, String password);

    List<Member> findAllMember();

}
