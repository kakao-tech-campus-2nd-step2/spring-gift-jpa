package gift.Model;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserInfoDAO {
    private final JdbcTemplate jdbcTemplate;

    public UserInfoDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createUserTable(){
        var sql = """
                create table userInfo(
                    email varchar(255) primary key,
                    password varchar(255),
                    role varchar(64)
                )
                """;
        jdbcTemplate.execute(sql);
    }

    public void insertUser(UserInfo userInfo){
        var sql = "insert into userInfo (email, password, role) values (?,?,?)";
        jdbcTemplate.update(sql, userInfo.email(), userInfo.password(), userInfo.role().name());
    }

    public void deleteUser(String email){
        var sql = "delete from userInfo where email=?";
        jdbcTemplate.update(sql, email);
    }

    public void updateUser(String email, UserInfo userInfo){
        var sql = "update userInfo set password=? where email =?";
        jdbcTemplate.update(sql,userInfo.password(),email);
    }

    public List<UserInfo> selectAllUser(){
        var sql = "select email, password, role from userInfo";
        List<UserInfo> users = jdbcTemplate.query(
                sql, (resultSet, rowNum) -> {
                    UserInfo user = new UserInfo(
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            Enum.valueOf(Role.class, resultSet.getString("role"))
                    );
                    return user;
                });
        return users;
    }

    public UserInfo selectUser(String email){
        try{
            var sql = "select email, password, role from userInfo where email=?";
            return jdbcTemplate.queryForObject(
                    sql,
                    (resultSet, rowNum) -> new UserInfo(
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            Enum.valueOf(Role.class, resultSet.getString("role"))
                    ),
                    email
            );
        }
        catch(EmptyResultDataAccessException e){
            return null;
        }
    }
}
