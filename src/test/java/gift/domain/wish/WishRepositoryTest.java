package gift.domain.wish;

import gift.domain.member.Member;
import gift.domain.product.Product;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WishRepositoryTest {

    @Autowired
    private WishRepository wishRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("delete 메서드 테스트")
    void deleteByMemberIdAndProductId() {
        //given
        Member member = createMember();
        Product product = createProduct();
        Wish expected = new Wish(member,product,1);
        wishRepository.save(expected);

        //when
        wishRepository.deleteByMemberIdAndProductId(member.getId(), product.getId());
        entityManager.flush();

        //then
        Optional<Wish> afterDelete = wishRepository.findByMemberIdAndProductId(member.getId(), product.getId());
        Assertions.assertThat(afterDelete).isNotPresent();
    }

    @Test
    @DisplayName("findByMemberIdAndProductId 메서드 테스트")
    void findByMemberIdAndProductId() {
        //given
        Member member = createMember();
        Product product = createProduct();
        Wish expected = new Wish(member,product,1);
        wishRepository.save(expected);

        //when
        //then
        Optional<Wish> actual = wishRepository.findByMemberIdAndProductId(member.getId(), product.getId());
        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual.get().getId()).isEqualTo(expected.getId())
        );
    }

    private Member createMember() {
        Member member = new Member("tester@gmail.com","더미","password",1);
        entityManager.persist(member);
        entityManager.flush();
        return member;
    }

    private Product createProduct() {
        Product product = new Product("커피",10000,"image");
        entityManager.persist(product);
        entityManager.flush();
        return product;
    }
}
