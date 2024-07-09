package gift.product.repository;

import gift.product.model.Member;

public interface AuthRepository {

    public boolean existsByEmail(String email);

    public boolean existsById(Long id);

    public void save(Member member);

    public Member findByEmail(String email);
}
