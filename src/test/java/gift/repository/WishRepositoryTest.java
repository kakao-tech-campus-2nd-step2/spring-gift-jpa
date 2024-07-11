package gift.repository;


import static org.assertj.core.api.Assertions.assertThat;

import gift.entity.Product;
import gift.entity.User;
import gift.entity.Wish;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class WishRepositoryTest {

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() throws Exception {
        userInsertion();
        productInsertion();
        wishInsertion();
    }

    @Test
    @DisplayName("user id를 통해 wishes 찾기")
    void findByUserId() {
        // given
        Product product1 = Product.builder()
            .id(1L)
            .name("Product A")
            .price(1000)
            .imageUrl("http://example.com/images/product_a.jpg")
            .build();
        Product product3 = Product.builder()
            .id(3L)
            .name("Product C")
            .price(3000)
            .imageUrl("http://example.com/images/product_c.jpg")
            .build();

        // when
        final List<Wish> actual = wishRepository.findByUserId(1L);

        // then
        assertThat(actual).isNotEmpty();
        assertThat(actual.size()).isEqualTo(2);

        // product1, quantity: 2
        assertThat(actual.getFirst().getProduct().getId()).isEqualTo(product1.getId());
        assertThat(actual.getFirst().getProduct().getName()).isEqualTo(product1.getName());
        assertThat(actual.getFirst().getProduct().getPrice()).isEqualTo(product1.getPrice());
        assertThat(actual.getFirst().getProduct().getImageUrl()).isEqualTo(product1.getImageUrl());
        assertThat(actual.getFirst().getQuantity()).isEqualTo(2);

        // product3, quantity: 1
        assertThat(actual.get(1).getProduct().getId()).isEqualTo(product3.getId());
        assertThat(actual.get(1).getProduct().getName()).isEqualTo(product3.getName());
        assertThat(actual.get(1).getProduct().getPrice()).isEqualTo(product3.getPrice());
        assertThat(actual.get(1).getProduct().getImageUrl()).isEqualTo(product3.getImageUrl());
        assertThat(actual.get(1).getQuantity()).isEqualTo(1);
    }

    @Test
    @DisplayName("새 위시 추가 테스트")
    void createTest() {
        // given
        Long userId = 1L;
        Long productId = 2L;
        Wish newWish = Wish.builder()
            .user(userRepository.findById(userId).get())
            .product(productRepository.findById(productId).get())
            .quantity(2)
            .build();

        // when
        final Wish actual = wishRepository.save(newWish);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getUser().getId()).isEqualTo(userId);
        assertThat(actual.getProduct().getId()).isEqualTo(productId);
        assertThat(actual.getQuantity()).isEqualTo(2);
    }

    @Test
    @DisplayName("wish update test")
    void updateWishTest() {
        // given
        Integer newQuantity = 50;

        // when
        final Wish wish = wishRepository.findById(1L).get();
        wish.changeQuantity(newQuantity);
        final Wish actual = wishRepository.findById(1L).get();

        // then
        assertThat(actual.getQuantity()).isEqualTo(newQuantity);
    }

    @Test
    @DisplayName("삭제 테스트")
    void deleteTest() {
        // given
        final Wish actual = wishRepository.findByUserId(1L).getFirst();
        Long actualId = actual.getId();

        // when
        wishRepository.delete(actual);

        // then
        assertThat(wishRepository.findById(actualId)).isNotPresent();
    }

    void productInsertion() {
        productRepository.deleteAll();
        entityManager.getEntityManager()
            .createNativeQuery("ALTER TABLE products ALTER COLUMN id RESTART WITH 1")
            .executeUpdate();

        Product product1 = Product.builder()
            .name("Product A")
            .price(1000)
            .imageUrl("http://example.com/images/product_a.jpg")
            .build();

        Product product2 = Product.builder()
            .name("Product B")
            .price(2000)
            .imageUrl("http://example.com/images/product_b.jpg")
            .build();

        Product product3 = Product.builder()
            .name("Product C")
            .price(3000)
            .imageUrl("http://example.com/images/product_c.jpg")
            .build();

        Product product4 = Product.builder()
            .name("Product D")
            .price(4000)
            .imageUrl("http://example.com/images/product_d.jpg")
            .build();

        Product product5 = Product.builder()
            .name("Product E")
            .price(5000)
            .imageUrl("http://example.com/images/product_e.jpg")
            .build();

        productRepository.saveAll(List.of(product1, product2, product3, product4, product5));
    }

    void userInsertion() {
        userRepository.deleteAll();
        entityManager.getEntityManager()
            .createNativeQuery("ALTER TABLE users ALTER COLUMN id RESTART WITH 1")
            .executeUpdate();

        User user1 = User.builder()
            .email("user1@example.com")
            .password("password1")
            .build();

        User user2 = User.builder()
            .email("user2@example.com")
            .password("password2")
            .build();

        User user3 = User.builder()
            .email("user3@example.com")
            .password("password3")
            .build();

        userRepository.saveAll(List.of(user1, user2, user3));
    }

    void wishInsertion() {
        wishRepository.deleteAll();
        entityManager.getEntityManager()
            .createNativeQuery("ALTER TABLE wishes ALTER COLUMN id RESTART WITH 1")
            .executeUpdate();

        Wish wish1 = Wish.builder()
            .user(userRepository.findById(1L).get())
            .product(productRepository.findById(1L).get())
            .quantity(2)
            .build();

        Wish wish2 = Wish.builder()
            .user(userRepository.findById(1L).get())
            .product(productRepository.findById(3L).get())
            .quantity(1)
            .build();

        Wish wish3 = Wish.builder()
            .user(userRepository.findById(2L).get())
            .product(productRepository.findById(2L).get())
            .quantity(5)
            .build();

        Wish wish4 = Wish.builder()
            .user(userRepository.findById(3L).get())
            .product(productRepository.findById(4L).get())
            .quantity(3)
            .build();

        wishRepository.saveAll(List.of(wish1, wish2, wish3, wish4));
    }

}
