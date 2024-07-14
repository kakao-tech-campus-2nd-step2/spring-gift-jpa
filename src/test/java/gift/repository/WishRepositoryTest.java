package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import gift.product.model.Member;
import gift.product.model.Product;
import gift.product.model.Wish;
import gift.product.repository.AuthRepository;
import gift.product.repository.ProductRepository;
import gift.product.repository.WishRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class WishRepositoryTest {

    @Autowired
    WishRepository wishRepository;

    @Autowired
    AuthRepository authRepository;

    @Autowired
    ProductRepository productRepository;

    @Test
    void 위시리스트_항목_추가() {
        Member member = authRepository.save(new Member("test@test.com", "1234"));
        Product product = productRepository.save(new Product("테스트1", 1500, "테스트주소1"));

        Wish wish = new Wish(member, product);
        Wish insertedWish = wishRepository.save(wish);

        assertSoftly(softly -> {
            assertThat(insertedWish.getId()).isNotNull();
            assertThat(insertedWish.getMember().getId()).isNotNull();
            assertThat(insertedWish.getProduct().getId()).isEqualTo(product.getId());
        });
    }

    @Test
    void 위시리스트_전체_조회() {
        Member member = authRepository.save(new Member("test@test.com", "1234"));
        Product product1 = productRepository.save(new Product("테스트1", 1500, "테스트주소1"));
        Product product2 = productRepository.save(new Product("테스트2", 3000, "테스트주소2"));

        wishRepository.save(new Wish(member, product1));
        wishRepository.save(new Wish(member, product2));

        List<Wish> wishes = wishRepository.findAllByMemberId(member.getId());

        assertThat(wishes).hasSize(2);
    }

    @Test
    void 위시리스트_조회() {
        Member member = authRepository.save(new Member("test@test.com", "1234"));
        Product product = productRepository.save(new Product("테스트1", 1500, "테스트주소1"));

        Wish wish = wishRepository.save(new Wish(member, product));

        Optional<Wish> foundWish = wishRepository.findByIdAndMemberId(wish.getId(), member.getId());

        assertThat(foundWish.isPresent()).isTrue();
        assertThat(foundWish.get().getProduct().getId()).isEqualTo(product.getId());
    }

    @Test
    void 위시리스트_항목_삭제() {
        Member member = authRepository.save(new Member("test@test.com", "1234"));
        Product product = productRepository.save(new Product("테스트1", 1500, "테스트주소1"));

        Wish wish = wishRepository.save(new Wish(member, product));
        wishRepository.deleteById(wish.getId());
        boolean isPresentWish = wishRepository.findByIdAndMemberId(wish.getId(), member.getId())
            .isPresent();

        assertThat(isPresentWish).isFalse();
    }
}
