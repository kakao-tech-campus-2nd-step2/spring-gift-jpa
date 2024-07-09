package gift.member.repository;

import gift.member.model.Member;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.*;

// JDBC 에 직접 연결
public interface MemberRepository extends CrudRepository<Member, Long> {
    // h2-console JDBC 연결 할때
    String URL = "jdbc:h2:mem:testdb";
    String USERNAME = "sa";
    String PASSWORD = "";

    // SQL 쿼리
    String SQL_INSERT_MEMBER = "INSERT INTO member (email, password) VALUES (?, ?)";
    String SQL_FIND_BY_EMAIL_AND_PASSWORD = "SELECT * FROM member WHERE email = ? AND password = ?";

    @Query("SELECT * FROM member WHERE email = :email AND password = :password")
    Member findByEmailAndPassword(String email, String password);

}