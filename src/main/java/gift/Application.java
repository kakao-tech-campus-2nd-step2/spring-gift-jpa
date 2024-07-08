package gift;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class Application implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... strings) throws Exception {
        /**
         * 임시 데이터 추가
         */
        jdbcTemplate.execute("CREATE TABLE product(id SERIAL, name VARCHAR(255),price int ,imageUrl VARCHAR(255))");

        jdbcTemplate.execute("CREATE TABLE member(id SERIAL, email VARCHAR(255), password VARCHAR(255))");

        jdbcTemplate.execute("CREATE TABLE authtoken (token VARCHAR(255), email varchar(255))");

        jdbcTemplate.execute("CREATE TABLE wish (id SERIAL, email VARCHAR(255), product_id INT, count INT)");

        jdbcTemplate.execute("INSERT INTO member (ID, EMAIL, PASSWORD) VALUES\n" +
                "(1, 'a', '1'),\n" +
                "(2, 'b', '2'),\n" +
                "(3, 'c', '3'),\n" +
                "(4, 'd', '4'),\n" +
                "(5, 'e', '5');");

        jdbcTemplate.execute("INSERT INTO product (ID, NAME, PRICE, IMAGEURL) VALUES\n" +
                "(1, 'Laptop', 999, 'http://example.com/images/laptop.jpg'),\n" +
                "(2, 'Smartphone', 599, 'http://example.com/images/smartphone.jpg'),\n" +
                "(3, 'Tablet', 399, 'http://example.com/images/tablet.jpg'),\n" +
                "(4, 'Smartwatch', 199, 'http://example.com/images/smartwatch.jpg'),\n" +
                "(5, 'Headphones', 149, 'http://example.com/images/headphones.jpg');");
    }

}
