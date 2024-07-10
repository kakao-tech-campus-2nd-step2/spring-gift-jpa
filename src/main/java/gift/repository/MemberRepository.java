package gift.repository;

import gift.domain.Member;

import java.util.Optional;

public interface MemberRepository {

    Member findById(long id);
    Member findByEmail(String email);
    void save(Member member);
    void update(Member member);
    void delete(Member member);
    boolean existsByEmail(String email);
}
