package gift.DAO;

import gift.dto.MemberDTO;
import gift.exception.NoSuchMemberException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAO {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public MemberDAO(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("MEMBER");
    }

    public MemberDTO findMember(String email) {
        var sql = "SELECT * FROM MEMBER WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, memberRowMapper(), email);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new NoSuchMemberException();
        }
    }

    public void register(MemberDTO memberDTO) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(memberDTO);
        simpleJdbcInsert.execute(parameters);
    }

    public void changePassword(MemberDTO memberDTO) {
        var sql = "UPDATE MEMBER SET password = ? WHERE email = ?";
        jdbcTemplate.update(sql, memberDTO.password(), memberDTO.email());
    }

    private RowMapper<MemberDTO> memberRowMapper() {
        return (resultSet, rowNum) -> new MemberDTO(
            resultSet.getString("email"),
            resultSet.getString("password"));
    }
}
