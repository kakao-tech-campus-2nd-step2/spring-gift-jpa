package gift.repository;

import gift.exception.MemberErrorCode;
import gift.exception.MemberException;
import gift.model.Member;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;

    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Member> selectAllMembers() {
        return jdbcTemplate.query(
            MemberQuery.SELECT_ALL_MEMBER.getQuery(),
            (resultSet, rowNum) -> new Member(
                resultSet.getLong("id"),
                resultSet.getString("email"),
                resultSet.getString("password"),
                resultSet.getString("name"),
                resultSet.getString("role")
            )
        );
    }

    public Member selectMemberByEmail(String email) throws MemberException {
        try {
            return jdbcTemplate.queryForObject(
                MemberQuery.SELECT_MEMBER_BY_EMAIL.getQuery(),
                (resultSet, rowNum) -> new Member(
                    resultSet.getLong("id"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    resultSet.getString("name"),
                    resultSet.getString("role")
                ),
                email
            );
        } catch (EmptyResultDataAccessException e) {
            throw new MemberException(MemberErrorCode.FAILURE_LOGIN);
        }
    }

    public Member selectMemberById(Long id) throws EmptyResultDataAccessException {
        return jdbcTemplate.queryForObject(
            MemberQuery.SELECT_MEMBER_BY_ID.getQuery(),
            (resultSet, rowNum) -> new Member(
                resultSet.getLong("id"),
                resultSet.getString("email"),
                resultSet.getString("password"),
                resultSet.getString("name"),
                resultSet.getString("role")
            ),
            id
        );
    }

    public void insertMember(Member member) {
        jdbcTemplate.update(MemberQuery.INSERT_MEMBER.getQuery(), member.getEmail(), member.getPassword(),
            member.getName(), member.getRole());
    }

    public void updateMemberByEmail(String email, Member member) {
        jdbcTemplate.update(MemberQuery.UPDATE_MEMBER_BY_EMAIL.getQuery(), member.getEmail(),
            member.getPassword(), member.getName(), member.getRole(), email);
    }

    public void deleteMemberByEmail(String email) {
        jdbcTemplate.update(MemberQuery.DELETE_MEMBER_BY_EMAIL.getQuery(), email);
    }
}
