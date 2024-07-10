//package gift.dao;
//
//import gift.model.Member;
//import gift.model.Product;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public class MemberDao {
//    private final JdbcTemplate jdbcTemplate;
//
//    public MemberDao(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public RowMapper<Member> MemberRowMapper() {
//        return (resultSet, rowNum) -> new Member(
//                resultSet.getLong("id"),
//                resultSet.getString("email"),
//                resultSet.getString("password")
//        );
//    }
//
//    public Member selectMember(String email){
//        var sql = "select id, email, password from member where email = ?";
//        try {
//            return jdbcTemplate.queryForObject(sql, MemberRowMapper(), email);
//        } catch (EmptyResultDataAccessException e) {
//            return null;
//        }
//    }
//
//    public void insertMember(Member member){
//        var sql = "insert into member (email, password) values (?, ?)";
//        jdbcTemplate.update(sql, member.getEmail(), member.getPassword());
//    }
//}
