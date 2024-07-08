package gift.model.dao;

public class UserQuery {
    public static String INSERT_USER = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
    public static String SELECT_ALL_USERS = "SELECT * FROM users";
    public static String SELECT_USER_BY_USERNAME = "SELECT * FROM users WHERE username = ?";
    public static String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    public static String UPDATE_USER = "UPDATE users SET username = ?, password = ?, role = ? WHERE id = ?";
    public static String DELETE_USER = "DELETE FROM users WHERE id = ?";
    public static String SELECT_USER_BY_USERNAME_AND_PASSWORD = "SELECT * FROM users WHERE username = ? AND password = ?";
}
