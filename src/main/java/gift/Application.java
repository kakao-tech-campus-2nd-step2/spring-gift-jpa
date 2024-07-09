package gift;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class Application implements CommandLineRunner{
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        jdbcTemplate.execute("DROP TABLE IF EXISTS products");
        jdbcTemplate.execute("CREATE TABLE products (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255), " +
                "price INT, " +
                "imageUrl VARCHAR(255))");
        
        jdbcTemplate.execute("DROP TABLE IF EXISTS users");
        jdbcTemplate.execute("CREATE TABLE users (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "email VARCHAR(255) NOT NULL, " +
                "password VARCHAR(255) NOT NULL)");
        
        jdbcTemplate.execute("DROP TABLE IF EXISTS wishlist");
        jdbcTemplate.execute("CREATE TABLE wishlist (" +
        		"id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
        		"user_id BIGINT, " +
        		"product_id BIGINT, " +
        		"quantity INT, " +
        		"FOREIGN KEY (user_id) REFERENCES users(id), " +
        		"FOREIGN KEY (product_id) REFERENCES products(id))");
    }
}
