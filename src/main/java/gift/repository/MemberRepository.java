package gift.repository;

import gift.model.Member;

public interface MemberRepository {
    Member save(Member member);

    Member findByEmail(String email);

    boolean existsById(Long id);

    boolean existsByEmail(String email);

    void deleteById(Long id);
}
