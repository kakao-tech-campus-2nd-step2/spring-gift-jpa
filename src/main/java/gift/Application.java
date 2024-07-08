package gift;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class Application implements CommandLineRunner {
    private final JdbcTemplate jdbcTemplate;

    public Application(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        /*
         * dummy data 삽입
         */
        jdbcTemplate.execute(
            "INSERT INTO products (id, name, price, imageUrl) VALUES (1, 'Product A', 1000, 'http://example.com/images/product_a.jpg');"
            + "INSERT INTO products (id, name, price, imageUrl) VALUES (2, 'Product B', 2000, 'http://example.com/images/product_b.jpg');"
            + "INSERT INTO products (id, name, price, imageUrl) VALUES (3, 'Product C', 3000, 'http://example.com/images/product_c.jpg');"
            + "INSERT INTO products (id, name, price, imageUrl) VALUES (4, 'Product D', 4000, 'http://example.com/images/product_d.jpg');"
            + "INSERT INTO products (id, name, price, imageUrl) VALUES (5, 'Product E', 5000, 'http://example.com/images/product_e.jpg')"
        );

        jdbcTemplate.execute(
            "INSERT INTO users (email, password) VALUES ('user1@example.com', 'password1');"
            + "INSERT INTO users (email, password) VALUES ('user2@example.com', 'password2');"
            + "INSERT INTO users (email, password) VALUES ('user3@example.com', 'password3');"
            + "INSERT INTO users (email, password) VALUES ('user4@example.com', 'password4');"
            + "INSERT INTO users (email, password) VALUES ('user5@example.com', 'password5')"
            );

        jdbcTemplate.execute(
            "INSERT INTO wishes (user_id, product_id, quantity) VALUES (1, 1, 2);"
            + "INSERT INTO wishes (user_id, product_id, quantity) VALUES (1, 3, 1);"
            + "INSERT INTO wishes (user_id, product_id, quantity) VALUES (2, 2, 1);"
            + "INSERT INTO wishes (user_id, product_id, quantity) VALUES (3, 4, 3);"
            + "INSERT INTO wishes (user_id, product_id, quantity) VALUES (4, 1, 1);"
            + "INSERT INTO wishes (user_id, product_id, quantity) VALUES (4, 5, 2);"
            + "INSERT INTO wishes (user_id, product_id, quantity) VALUES (5, 2, 1);"
            + "INSERT INTO wishes (user_id, product_id, quantity) VALUES (5, 3, 1);"
            + "INSERT INTO wishes (user_id, product_id, quantity) VALUES (5, 4, 1)"
        );
    }
}
