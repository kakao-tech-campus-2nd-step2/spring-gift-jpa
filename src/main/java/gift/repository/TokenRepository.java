package gift.repository;

import gift.dto.Token;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class TokenRepository {

    private final JdbcTemplate jdbcTemplate;

    public TokenRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void initialize() {
        createTokenTable();
        saveToken(new Token(1L, "1234"));
    }

    private void createTokenTable() {
        var sql = """
                create table token (
                  memberId bigint unique,
                  tokenValue varchar(255),
                  primary key(memberId)
                )
                """;
        jdbcTemplate.execute(sql);
    }

    public Token getTokenByMemberId(Long memberId) {
        String sql = "select * from token where memberId = ?";
        return jdbcTemplate.queryForObject(sql, new TokenRowMapper(), memberId);
    }

    public void saveToken(Token createdToken) {
        String sql = "INSERT INTO token (memberId, tokenValue) VALUES (?, ?)";
        jdbcTemplate.update(sql, createdToken.getMemberId(), createdToken.getValue());
    }

    public boolean containsToken(String tokenValue) {
        String sql = "SELECT COUNT(*) FROM token WHERE tokenValue = ?";
        int affectedCount = jdbcTemplate.queryForObject(sql, new Object[]{tokenValue}, Integer.class);
        return affectedCount > 0;
    }

    public Long getMemberIdByToken(String tokenValue) {
        String sql = " select memberId from token where tokenValue = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{tokenValue}, Long.class);
    }

    public void deleteAll() {
        String sql = "delete from token";
        jdbcTemplate.update(sql);
    }

    private static class TokenRowMapper implements RowMapper<Token> {
        @Override
        public Token mapRow(ResultSet rs, int rowNum) throws SQLException {
            Token token = new Token(rs.getLong("memberId"), rs.getString("tokenValue"));
            return token;
        }
    }

}
