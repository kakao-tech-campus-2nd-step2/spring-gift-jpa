package gift.repository;

import gift.entity.Member;
import gift.entity.Product;
import gift.entity.Wish;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class WishRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private WishRepository wishRepository;

    private Member member;

    private Product product;

    private Wish wish;

    @BeforeEach
    void setUp() {
        member = new Member("test@email.com", "password");
        entityManager.persist(member);

        product = new Product(5000, "gimbab", "https://image.com/1");
        entityManager.persist(product);

        wish = new Wish(member, product);
        entityManager.persist(wish);
        product.addWish(wish);
        member.addWish(wish);

        entityManager.flush();
    }

    @Test
    void memberCascadeDeleteTest() {
        entityManager.remove(member);

        Wish resultOfFind = wishRepository.findById(wish.getId())
                .orElse(null);

        Assertions.assertThat(resultOfFind)
                .isNull();
    }

    @Test
    void productCascadeDeleteTest() {
        entityManager.remove(product);

        Wish resultOfFind = wishRepository.findById(wish.getId())
                .orElse(null);

        Assertions.assertThat(resultOfFind)
                .isNull();
    }
}
