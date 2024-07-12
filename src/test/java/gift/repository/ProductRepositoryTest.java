package gift.repository;

import gift.domain.Member;
import gift.domain.Product;
import gift.domain.Wish;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    private ProductRepository products;
    @Autowired
    private EntityManager entityManager;

    private String expectedName;
    private int expectedPrice;
    private String expectedImageUrl;
    private Product expectedProduct;
    private Member expectedMember;
    private Wish expectedWish;

    @BeforeEach
    void setUp(){
        expectedName = "아메리카노";
        expectedPrice = 2000;
        expectedImageUrl = "http://example.com/americano";

        expectedProduct = new Product(expectedName, expectedPrice, expectedImageUrl);

        expectedMember = new Member("a@a.com", "1234");
        expectedWish = new Wish(expectedMember, expectedProduct, 1);
    }

    @Test
    @DisplayName("상품 저장 테스트")
    void save() {
        // when
        Product actual = products.save(expectedProduct);

        // then
        assertAll(
                ()->assertThat(actual.getId()).isNotNull(),
                ()->assertThat(actual.getName()).isEqualTo(expectedProduct.getName()),
                ()->assertThat(actual.getPrice()).isEqualTo(expectedProduct.getPrice()),
                ()->assertThat(actual.getImageUrl()).isEqualTo(expectedProduct.getImageUrl())
        );
    }

    @Test
    @DisplayName("상품 아이디 조회 테스트")
    void findById() {
        // given
        Product savedProduct = products.save(expectedProduct);
        entityManager.flush();
        entityManager.clear();

        // when
        Product findProduct = products.findById(savedProduct.getId()).get();

        // then
        assertAll(
                () -> assertThat(findProduct.getId()).isNotNull(),
                () -> assertThat(findProduct.getName()).isEqualTo(expectedName),
                () -> assertThat(findProduct.getPrice()).isEqualTo(expectedPrice),
                () -> assertThat(findProduct.getImageUrl()).isEqualTo(expectedImageUrl)
        );
    }

    @Test
    @DisplayName("상품 이름 조회 테스트")
    void findByName() {
        // given
        Product savedProduct = products.save(expectedProduct);
        entityManager.flush();
        entityManager.clear();

        // when
        Product findProduct = products.findByName(savedProduct.getName()).get();

        // then
        assertAll(
                () -> assertThat(findProduct.getId()).isNotNull(),
                () -> assertThat(findProduct.getName()).isEqualTo(expectedName),
                () -> assertThat(findProduct.getPrice()).isEqualTo(expectedPrice),
                () -> assertThat(findProduct.getImageUrl()).isEqualTo(expectedImageUrl)
        );
    }

    @Test
    @DisplayName("상품 전체 조회 테스트")
    void findAll() {
        // given
        Product product1 = products.save(new Product("상품1", 1000, "http://product1"));
        Product product2 = products.save(new Product("상품2", 2000, "http://product2"));
        Product product3 = products.save(new Product("상품3", 3000, "http://product3"));
        entityManager.flush();
        entityManager.clear();

        // when
        List<Product> findProducts = products.findAll();

        // then
        assertThat(findProducts.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("상품 아이디로 삭제 테스트")
    void deleteById() {
        // given
        Product savedProduct = products.save(expectedProduct);

        // when
        products.deleteById(savedProduct.getId());

        // then
        List<Product> findProducts = products.findAll();
        assertAll(
                () -> assertThat(findProducts.size()).isEqualTo(0)
        );
    }

    @Test
    @DisplayName("상품->위시 영속 전파 테스트")
    void testCascadePersist(){
        // given
        entityManager.persist(expectedMember);

        // when
        expectedProduct.addWish(expectedWish);
        Product savedProduct = products.save(expectedProduct);
        entityManager.flush();
        entityManager.clear();

        // then
        Product foundProduct = products.findById(savedProduct.getId()).get();
        assertAll(
                () -> assertThat(foundProduct).isEqualTo(savedProduct),
                () -> assertThat(foundProduct.getWishes().size()).isEqualTo(1),
                () -> assertThat(foundProduct.getWishes().get(0)).isEqualTo(expectedWish)
        );
    }

    @Test
    @DisplayName("상품->위시 삭제 전파 테스트")
    void testCascadeRemove(){
        // given
        entityManager.persist(expectedMember);

        expectedProduct.addWish(expectedWish);
        Product savedProduct = products.save(expectedProduct);
        entityManager.flush();
        entityManager.clear();

        // when
        Product foundProduct = products.findById(savedProduct.getId()).get();
        products.deleteById(foundProduct.getId());
        entityManager.flush();
        entityManager.clear();

        //then
        List<Product> findProducts = products.findAll();
        Wish deletedWish = entityManager.find(Wish.class, expectedWish.getId());
        assertAll(
                () -> assertThat(findProducts.size()).isEqualTo(0),
                () -> assertThat(deletedWish).isNull()
        );
    }

    @Test
    @DisplayName("고아 객체 제거 테스트")
    void testOrphanRemoval(){
        // given
        entityManager.persist(expectedMember);

        expectedProduct.addWish(expectedWish);
        Product savedProduct = products.save(expectedProduct);
        entityManager.flush();
        entityManager.clear();

        // when
        Product foundProduct = products.findById(savedProduct.getId()).get();
        Wish foudnWish = foundProduct.getWishes().get(0);
        foundProduct.removeWish(foudnWish);
        entityManager.flush();
        entityManager.clear();

        // then
        Wish orphanedWish = entityManager.find(Wish.class, expectedWish.getId());
        assertThat(orphanedWish).isNull();
    }

    @Test
    @DisplayName("지연 로딩 테스트")
    void testLazyFetch(){
        // given
        entityManager.persist(expectedMember);

        Wish expected = new Wish(expectedMember, expectedProduct, 1);
        expectedProduct.addWish(expected);
        expectedProduct.addWish(new Wish(expectedMember, expectedProduct, 2));
        expectedProduct.addWish(new Wish(expectedMember, expectedProduct, 3));
        expectedProduct.addWish(new Wish(expectedMember, expectedProduct, 4));
        Product savedProduct = products.save(expectedProduct);
        entityManager.flush();
        entityManager.clear();

        // when
        // Product 조회 (지연 로딩이므로 연관관계 조회 안함, Product 객체만 조회함)
        Product foundProduct = products.findById(savedProduct.getId()).get();

        // Wish 조회 (Wish 객체도 조회함)
        List<Wish> wishes = foundProduct.getWishes();

        // then
        assertAll(
                () -> assertThat(wishes.size()).isEqualTo(4),
                () -> assertThat(wishes.get(0)).isEqualTo(expected)
        );
    }

    @Test
    @DisplayName("상품 페이지 조회 테스트")
    void testFindAll() {
        // given
        products.save(new Product("상품1", 1000, "http://product1"));
        products.save(new Product("상품2", 2000, "http://product2"));
        products.save(new Product("상품3", 3000, "http://product3"));

        // when
        Pageable pageable = PageRequest.of(0,2, Sort.by("id").descending());
        Page<Product> page = products.findAll(pageable);

        // then
        assertAll(
                () -> assertThat(page).isNotNull(),
                () -> assertThat(page.getContent().size()).isEqualTo(2),
                () -> assertThat(page.getContent().get(0).getName()).isEqualTo("상품3"),
                () -> assertThat(page.getTotalElements()).isEqualTo(3),
                () -> assertThat(page.getTotalPages()).isEqualTo(2)
        );
    }
}