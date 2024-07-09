package gift.repository.mapper;

import gift.domain.Member;
import gift.domain.vo.Email;
import gift.domain.vo.Password;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class MemberRowMapper implements RowMapper<Member> {

    @Override
    public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Member.Builder()
            .id(rs.getLong("member_id"))
            .email(Email.from(rs.getString("email")))
            .password(Password.from(rs.getString("password")))
            .name(rs.getString("name"))
            .build();
    }
}
