package gift.dao;

import gift.vo.Member;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberMapper implements RowMapper<Member> {

    @Override
    public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Member(
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("role")
        );
    }

}
