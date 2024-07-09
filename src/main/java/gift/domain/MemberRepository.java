package gift.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByToken(String token);
    Member findById(int id);
    int searchIdByToken(String token);
    String searchTokenByToken(String token);
    String searchTokenByEmailAndPassword(String email, String password);
    void save(String email, String password, String token);
}