package gift.repository;

import gift.dto.Member;
import java.sql.Types;
import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

    private final JdbcClient jdbcClient;
    private final RowMapper<Member> memberRowMapper = ((rs, rowNum) ->
        new Member(
            rs.getString("email"),
            rs.getString("password")
        ));

    public MemberDao(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    /**
     * 회원을 DB에 등록
     *
     * @param member
     * @return Integer
     */
    public Integer register(Member member) {
        String sql = """
            INSERT INTO member (email, password)
            VALUES (:email, :password);
            """;
        return jdbcClient.sql(sql)
            .param("email", member.getEmail(), Types.VARCHAR)
            .param("password", member.getPassword(), Types.VARCHAR)
            .update();
    }

    /**
     * email로 회원 조회
     *
     * @param email
     * @return Optional<Member>
     */
    public Optional<Member> findByEmail(String email) {
        String sql = "SELECT * FROM member WHERE email = :email";
        return jdbcClient.sql(sql)
            .param("email", email)
            .query(memberRowMapper)
            .optional();
    }
}
