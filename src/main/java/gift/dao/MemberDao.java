package gift.dao;

import gift.vo.Member;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

/**
 * JdbcClient 이용한 DB 접속
 */
@Repository
public class MemberDao {

    private final JdbcClient jdbcClient;

    public MemberDao(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Member findMemberByEmailAndPassword(String email, String password) {
    String sql = "SELECT * FROM member WHERE email = :email and password = :password";
        return this.jdbcClient.sql(sql)
                .param("email", email)
                .param("password", password)
                .query(new MemberMapper()).single();
    }

    public Boolean addMember(Member member) {
        String sql = "INSERT INTO member (email, password, role) VALUES (:email, :password, :role)";
        int resultRowNum = this.jdbcClient.sql(sql)
                .param("email", member.getEmail())
                .param("password", member.getPassword())
                .param("role", String.valueOf(member.getRole()))
                .update();
        if (resultRowNum != 1) {
            throw new RuntimeException("회원 추가 중 데이터베이스 오류가 발생했습니다. ");
        }
        return true;
    }

}
