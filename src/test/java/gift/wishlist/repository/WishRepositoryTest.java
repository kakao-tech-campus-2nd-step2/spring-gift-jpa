package gift.wishlist.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.wishlist.model.Wish;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
@Import(WishRepository.class)
class WishRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private WishRepository wishRepository;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS wishes CASCADE");
        jdbcTemplate.execute("DROP TABLE IF EXISTS members CASCADE");

        jdbcTemplate.execute("CREATE TABLE members (id BIGINT AUTO_INCREMENT PRIMARY KEY, email VARCHAR(255), password VARCHAR(255))");
        jdbcTemplate.execute("CREATE TABLE wishes (id BIGINT AUTO_INCREMENT PRIMARY KEY, member_id BIGINT NOT NULL, product_name VARCHAR(255) NOT NULL, FOREIGN KEY (member_id) REFERENCES members(id))");

        jdbcTemplate.execute("INSERT INTO members (email, password) VALUES ('user@example.com','password')");
        jdbcTemplate.execute("INSERT INTO wishes (member_id, product_name) VALUES (1, 'Product1')");

        wishRepository = new WishRepository(jdbcTemplate, jdbcTemplate.getDataSource());
    }

    @Test
    void testFindByMemberId() {
        List<Wish> wishList = wishRepository.findByMemberId(1L);
        assertThat(wishList).isNotEmpty();
        assertThat(wishList.get(0).getProductName()).isEqualTo("Product1");
    }

    @Test
    void testFindByName() {
        Optional<Wish> wish = wishRepository.findByName(1L,"Product1");
        assertThat(wish).isPresent();
        assertThat(wish.get().getProductName()).isEqualTo("Product1");
    }

    @Test
    void testFindByName_NotFound() {
        Optional<Wish> wish = wishRepository.findByName(2L,"No_Product");
        assertThat(wish).isNotPresent();
    }

    @Test
    void testSave() {
        Wish wish = new Wish();
        wish.setMemberId(1L);
        wish.setProductName("Product2");

        wishRepository.save(wish);

        assertThat(wish.getId()).isNotNull();
        assertThat(wish.getProductName()).isEqualTo("Product2");

        List<Wish> wishList = wishRepository.findByMemberId(1L);
        assertThat(wishList).hasSize(2);
    }

    @Test
    void testDeleteByProductName() {
        wishRepository.deleteByProductName(1L, "Product1");

        List<Wish> wishList = wishRepository.findByMemberId(1L);
        assertThat(wishList).isEmpty();
    }
}