package gift.wishlist;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.member.Member;
import gift.member.MemberRepository;
import gift.product.Product;
import gift.product.ProductRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class WishlistRepositoryTest {

    @Autowired
    WishlistRepository wishlistRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setupMemberAndProduct() {
        jdbcTemplate.execute("DROP ALL OBJECTS");
        jdbcTemplate.execute(
            """
                CREATE TABLE product
                (
                    id       INT AUTO_INCREMENT PRIMARY KEY,
                    name     VARCHAR(255),
                    price    INT,
                    imageUrl VARCHAR(255)
                );               
                CREATE TABLE member
                (
                    email    VARCHAR(255) PRIMARY KEY,
                    password VARCHAR(255)
                );               
                CREATE Table wishlist
                (
                    product_id   INT,
                    member_email VARCHAR(255),
                    PRIMARY KEY (product_id, member_email),
                    FOREIGN KEY (product_id) REFERENCES product (id),
                    FOREIGN KEY (member_email) REFERENCES member (email)
                )
                """
        );

        memberRepository.addMember(new Member("aaa@email.com", "password"));
        memberRepository.addMember(new Member("bbb@email.com", "password"));

        for (int i = 1; i < 4; i++) {
            productRepository.addProduct(
                new Product(i, "product-" + i, i * 100, "product-" + i + "-image"));
        }
    }

    @Test
    @DisplayName("[Unit] getAllWishlists test")
    void getAllWishlistsTest() {
        //given
        List<Product> expect = List.of(
            new Product(1L, "product-1", 100, "product-1-image"),
            new Product(2L, "product-2", 200, "product-2-image"),
            new Product(3L, "product-3", 300, "product-3-image")
        );

        for (Product product : expect) {
            wishlistRepository.addWishlist(new Wishlist(product.id(), "aaa@email.com"));
        }
        productRepository.addProduct(new Product(4L, "product-4", 400, "product-4-image"));
        wishlistRepository.addWishlist(new Wishlist(4L, "bbb@email.com"));

        //when
        List<Product> actual = wishlistRepository.getAllWishlists("aaa@email.com");

        //then
        assertAll(
            () -> assertThat(actual.get(0)).isEqualTo(expect.get(0)),
            () -> assertThat(actual.get(1)).isEqualTo(expect.get(1)),
            () -> assertThat(actual.get(2)).isEqualTo(expect.get(2))
        );
    }

    @Test
    @DisplayName("[Unit] addWishlist test")
    void addWishlistTest() {
        //given
        Wishlist expect = new Wishlist(1L, "aaa@email.com");

        //when
        wishlistRepository.addWishlist(expect);
        Wishlist actual = jdbcTemplate.queryForObject(
            """
                SELECT * FROM WISHLIST
                WHERE PRODUCT_ID = ? AND MEMBER_EMAIL = ?
                """,
            (rs, rowNum) -> new Wishlist(
                rs.getLong("PRODUCT_ID"),
                rs.getString("MEMBER_EMAIL")
            ),
            expect.productId(), expect.memberEmail()
        );

        //then
        assertThat(actual).isEqualTo(expect);
    }

    @Test
    @DisplayName("[Unit] deleteWishlist test")
    void deleteWishlistTest() {
        //given
        Wishlist expect = new Wishlist(1L, "aaa@email.com");
        wishlistRepository.addWishlist(expect);

        //when
        wishlistRepository.deleteWishlist(expect);
        Throwable actual = catchThrowable(
            () -> jdbcTemplate.queryForObject(
                """
                    SELECT * FROM WISHLIST
                    WHERE PRODUCT_ID = ? AND MEMBER_EMAIL = ?
                    """,
                (rs, rowNum) -> new Wishlist(
                    rs.getLong("PRODUCT_ID"),
                    rs.getString("MEMBER_EMAIL")
                ),
                expect.productId(), expect.memberEmail()
            )
        );

        //then
        assertThat(actual).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("[Unit] existWishlist test")
    void existWishlistTest() {
        //given
        Wishlist expect = new Wishlist(1L, "aaa@email.com");
        wishlistRepository.addWishlist(expect);

        //when
        Boolean trueCase = wishlistRepository.existWishlist(expect);
        Boolean falseCase = wishlistRepository.existWishlist(new Wishlist(2L, "aaa@email.com"));

        //then
        assertAll(
            () -> assertThat(trueCase).isTrue(),
            () -> assertThat(falseCase).isFalse()
        );
    }
}