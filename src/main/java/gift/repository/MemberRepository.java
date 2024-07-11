package gift.repository;

import gift.model.Member;

import java.util.Optional;

public interface MemberRepository {
    Optional<Member> findByEmail(String email);
    Member save(Member member);
}
