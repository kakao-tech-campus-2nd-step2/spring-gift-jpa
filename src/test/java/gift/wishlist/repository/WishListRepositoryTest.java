package gift.wishlist.repository;

import gift.member.model.Member;
import gift.member.repository.MemberRepository;
import gift.product.model.Product;
import gift.product.repository.ProductRepository;
import gift.wishlist.model.WishList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
public class WishListRepositoryTest {

    @Autowired
    private WishListRepository wishListRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        Member member = new Member("example@test.com", "password");
        memberRepository.save(member);
    }

    @Test
    public void saveWish() {
        Member member = memberRepository.findByEmail("example@test.com").orElse(null);
        Product product = new Product("test", 100, "https://www.google.com");
        product = productRepository.save(product);

        WishList wish = new WishList(member, product);
        wish = wishListRepository.save(wish);

        assertThat(wishListRepository.existsById(wish.getId())).isTrue();
    }

    @Test
    public void removeWish() {
        Member member = memberRepository.findByEmail("example@test.com").orElse(null);
        Product product = new Product("test", 100, "https://www.google.com");
        product = productRepository.save(product);

        WishList wish = new WishList(member, product);
        wish = wishListRepository.save(wish);

        wishListRepository.deleteById(wish.getId());

        assertThat(wishListRepository.existsById(wish.getId())).isFalse();
    }

    @Test
    public void existsByUserIdAndProductId() {
        Member member = memberRepository.findByEmail("example@test.com").orElse(null);
        Product product = new Product("test", 100, "https://www.google.com");
        product = productRepository.save(product);

        WishList wish = new WishList(member, product);
        wish = wishListRepository.save(wish);

        assertThat(wishListRepository.existsById(member.id(), product.id())).isTrue();
    }

    @Test
    public void findAllByUserId() {
        Member member = memberRepository.findByEmail("example@test.com").orElse(null);
        Product product = new Product("test", 100, "https://www.google.com");
        product = productRepository.save(product);

        WishList wish = new WishList(member, product);
        wishListRepository.save(wish);

        assertThat(wishListRepository.findByMember(member)).hasSize(1);
    }
}