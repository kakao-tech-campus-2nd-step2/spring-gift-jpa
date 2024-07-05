package gift.repository;

import gift.domain.Member;

import java.util.Optional;

public interface MemberRepository {
    Optional<Member> findByEmail(String email);
    void save(Member member);
}
