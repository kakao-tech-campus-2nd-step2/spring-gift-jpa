package gift.repository;

import gift.domain.Member;

import java.util.Optional;

public interface MemberRepository {
    void save(Member member);
    Optional<Member> findByPasswordAndEmail(String email, String password);
    Optional<Member> findByEmail(String email);
}