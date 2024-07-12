package gift.domain.repository.old;

//@Repository
@Deprecated
public class OldMemberRepository {

//    private final JdbcTemplate jdbcTemplate;
//
//    public OldMemberRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    private RowMapper<Member> getRowMapper() {
//        return (resultSet, rowNum) -> new Member(
//            resultSet.getLong("id"),
//            resultSet.getString("email"),
//            resultSet.getString("password"),
//            resultSet.getString("permission")
//        );
//    }
//
//    public Member save(MemberRequest requestDto) {
//        String sql = "INSERT INTO Members (email, password, permission) VALUES (?, ?, ?)";
//
//        jdbcTemplate.update(sql, requestDto.email(), HashUtil.hashCode(requestDto.password()), "Member");
//        return findByEmail(requestDto.email()).orElseThrow(RuntimeException::new);
//    }
//
//    public Optional<Member> findById(Long id) {
//        try {
//            String sql = "SELECT * FROM Members WHERE id = ?";
//            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, getRowMapper(), id));
//        } catch (EmptyResultDataAccessException e) {
//            return Optional.empty();
//        }
//    }
//
//    public Optional<Member> findByEmail(String email) {
//        try {
//            String sql = "SELECT * FROM Members WHERE email = ?";
//            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, getRowMapper(), email));
//        } catch (EmptyResultDataAccessException e) {
//            return Optional.empty();
//        }
//    }
//
//    public Optional<Member> updatePassword(MemberRequest requestDto) {
//        String sql = "UPDATE Members SET password = ? WHERE email = ?";
//        jdbcTemplate.update(sql, HashUtil.hashCode(requestDto.password()), requestDto.email());
//        return findByEmail(requestDto.email());
//    }
//
//    public Optional<Member> updatePermission(MemberPermissionChangeRequest requestDto) {
//        String sql = "UPDATE Members SET permission = ? WHERE email = ?";
//        jdbcTemplate.update(sql, requestDto.permission(), requestDto.email());
//        return findByEmail(requestDto.email());
//    }
//
//    public void deleteByEmail(String email) {
//        String sql = "DELETE FROM Members WHERE email = ?";
//        jdbcTemplate.update(sql, email);
//    }
}
