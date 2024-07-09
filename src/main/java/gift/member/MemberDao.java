package gift.member;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {
    @Autowired
    private JdbcClient jdbcClient;

    public MemberDao(JdbcClient jdbcClient) {this.jdbcClient = jdbcClient;}

    public void insertMember(Member member) {
        String sql = """
            INSERT INTO member (id, name, email, password, role) 
            VALUES (?,?,?,?,?)
            """;
        jdbcClient.sql(sql)
            .param(member.getId())
            .param(member.getName())
            .param(member.getEmail())
            .param(member.getPassword())
            .param(member.isRole())
            .update();
    }

    public Optional<Member> findMember(Member member) {
        String sql = """
            SELECT *
            FROM member 
            WHERE email = ? AND password = ?
            """;
        return jdbcClient.sql(sql)
            .param(member.getEmail())
            .param(member.getPassword())
            .query(Member.class).optional();
    }

    public Member findMemberById(String userEmail) {
        String sql = """
            SELECT * 
            FROM member 
            WHERE email = ?
            """;
        return jdbcClient.sql(sql)
            .param(userEmail)
            .query(Member.class).single();
    }

    public List<Member> findAllMember() {
        String sql = """
            SELECT 
              email, 
              password
            FROM member
            """;
        List<Member> members = jdbcClient.sql(sql).query(Member.class).list();
        return members;
    }
}

