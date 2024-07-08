package gift.dao;

import gift.model.member.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class MemberDao {
    private final JdbcTemplate jdbcTemplate;

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void registerNewMember(Member member){
        var sql = "insert into members (email, password) values (?,?)";
        jdbcTemplate.update(sql,member.getEmail(),member.getPassword());
    }

    public Member findByEmail(String email){
        var sql = "select email, password from members where email = ?";
        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    (resultSet, rowNum) -> new Member(
                            resultSet.getString("email"),
                            resultSet.getString("password")
                    ),
                    email
            );
        } catch (org.springframework.dao.EmptyResultDataAccessException e){
            return null;
        }
    }
}
