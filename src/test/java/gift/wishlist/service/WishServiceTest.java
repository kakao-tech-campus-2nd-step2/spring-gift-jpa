package gift.wishlist.service;

import static org.assertj.core.api.Assertions.assertThat;

import gift.member.model.Member;
import gift.member.repository.MemberRepository;
import gift.product.model.Product;
import gift.product.repository.ProductRepository;
import gift.wishlist.dto.WishResponse;
import gift.wishlist.model.Wish;
import gift.wishlist.repository.WishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
class WishServiceTest {

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    private WishService wishService;

    private Member member;
    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        member = new Member();
        member.setEmail("user@example.com");
        member.setPassword("password");
        member = memberRepository.save(member);

        product1 = new Product("Sample Product 1", 100, "http://example.com/product1.jpg");
        product2 = new Product("Sample Product 2", 200, "http://example.com/product2.jpg");
        product1 = productRepository.save(product1);
        product2 = productRepository.save(product2);

        wishRepository.save(new Wish(member, product1));
        wishRepository.save(new Wish(member, product2));

        wishService = new WishService(wishRepository);
    }

    @Test
    void testGetWishesPaged() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<WishResponse> wishesPage = wishService.getWishes(member, pageable);

        assertThat(wishesPage).isNotNull();
        assertThat(wishesPage.getContent()).hasSize(2);
        assertThat(wishesPage.getTotalElements()).isEqualTo(2);
        assertThat(wishesPage.getTotalPages()).isEqualTo(1);
        assertThat(wishesPage.getContent().get(0).getProduct().getName()).isEqualTo(
            "Sample Product 1");
        assertThat(wishesPage.getContent().get(1).getProduct().getName()).isEqualTo(
            "Sample Product 2");
    }

}