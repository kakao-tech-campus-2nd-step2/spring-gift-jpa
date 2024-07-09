package gift.dao;

import gift.model.member.Member;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;


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

    public Optional<Member> findByEmail(String email){
        var sql = "select email, password from members where email = ?";
        try {
            Member member = jdbcTemplate.queryForObject(
                    sql,
                    (resultSet, rowNum) -> new Member(
                            resultSet.getString("email"),
                            resultSet.getString("password")
                    ),
                    email
            );
            return Optional.ofNullable(member);
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }
}
