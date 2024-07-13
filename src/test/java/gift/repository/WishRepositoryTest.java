package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import gift.domain.member.entity.Member;
import gift.domain.member.repository.MemberRepository;
import gift.domain.product.entity.Product;
import gift.domain.wishlist.entity.Wish;
import gift.domain.product.repository.ProductRepository;
import gift.domain.wishlist.repository.WishRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
class WishRepositoryTest {

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("memberId로 wish 리스트 가져오는 findAllByMember 테스트")
    void findAllByMemberEntity() {
        // given
        Member requestMember = new Member("test", "password");
        Member savedMember = memberRepository.save(requestMember);

        Product requestProduct1 = new Product("product1", 1000, "product1.jpg");
        Product requestProduct2 = new Product("product2", 2000, "product2.jpg");
        Product savedProduct1 = productRepository.save(requestProduct1);
        Product savedProduct2 = productRepository.save(requestProduct2);

        Wish wish1 = new Wish(savedMember, savedProduct1);
        Wish wish2 = new Wish(savedMember, savedProduct2);

        Wish expected1 = wishRepository.save(wish1);
        Wish expected2 = wishRepository.save(wish2);

        Pageable pageable = PageRequest.of(0, 10);
        // when
        Page<Wish> actual = wishRepository.findAllByMember(savedMember, pageable);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual).hasSize(2);
        assertThat(actual).containsExactlyInAnyOrder(expected1, expected2);
    }

    @Test
    @DisplayName("findById 테스트")
    void findById() {
        // given
        Member requestMember = new Member("test", "password");
        Member savedMember = memberRepository.save(requestMember);

        Product requestProduct = new Product("product", 1000, "product1.jpg");
        Product savedProduct = productRepository.save(requestProduct);

        Wish requestWish = new Wish(savedMember, savedProduct);
        Wish expected = wishRepository.save(requestWish);

        // when
        Wish actual = wishRepository.findById(expected.getId()).orElseThrow();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("save 테스트")
    void save() {
        // given
        Member requestMember = new Member("test", "password");
        Member savedMember = memberRepository.save(requestMember);

        Product requestProduct = new Product("product", 1000, "product1.jpg");
        Product savedProduct = productRepository.save(requestProduct);

        Wish expected = new Wish(savedMember, savedProduct);

        // when
        Wish actual = wishRepository.save(expected);

        // then
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getMember()).isEqualTo(expected.getMember()),
            () -> assertThat(actual.getProduct()).isEqualTo(expected.getProduct())
        );
    }

    @Test
    @DisplayName("delete 테스트")
    void delete() {
        // given
        Member requestMember = new Member("test", "password");
        Member savedMember = memberRepository.save(requestMember);

        Product requestProduct = new Product("product", 1000, "product1.jpg");
        Product savedProduct = productRepository.save(requestProduct);

        Wish request = new Wish(savedMember, savedProduct);
        Wish savedWish = wishRepository.save(request);

        // when
        wishRepository.delete(savedWish);

        // then
        assertTrue(wishRepository.findById(savedWish.getId()).isEmpty());
    }
}