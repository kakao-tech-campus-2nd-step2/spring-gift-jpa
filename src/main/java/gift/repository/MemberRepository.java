package gift.repository;

import gift.domain.Member;

import java.util.Optional;

public interface MemberRepository {

    Optional<Member> findById(long id);
    Optional<Member> findByEmail(String email);
    void save(Member member);
    void update(Member member);
    void delete(Member member);
    boolean existsByEmail(String email);
}
