package gift.Repository;

import gift.Model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String email = rs.getString("email");
        String name = rs.getString("name");
        String password = rs.getString("password");
        boolean isAdmin = rs.getBoolean("isAdmin");

        return new User(id, email, password, name, isAdmin);
    }
}
