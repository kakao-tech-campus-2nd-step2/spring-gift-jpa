package gift.member.repository;

import gift.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    // h2-console JDBC 연결 할때
    String URL = "jdbc:h2:mem:testdb";
    String USERNAME = "sa";
    String PASSWORD = "";

    // SQL 쿼리
    String SQL_INSERT_MEMBER = "INSERT INTO member (email, password) VALUES (?, ?)";
    String SQL_FIND_BY_EMAIL_AND_PASSWORD = "SELECT * FROM member WHERE email = ? AND password = ?";

    // @Query 어노테이션을 사용하여 직접 SQL 쿼리 작성
    @Query("SELECT m FROM Member m WHERE m.email = :email AND m.password = :password")
    default Member findByEmailAndPassword(String email, String password) {
        return null;
    }

}