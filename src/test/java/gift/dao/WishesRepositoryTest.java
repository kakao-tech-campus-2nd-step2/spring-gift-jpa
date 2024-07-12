package gift.dao;

import gift.member.dao.MemberRepository;
import gift.member.entity.Member;
import gift.product.dao.ProductRepository;
import gift.product.entity.Product;
import gift.wishlist.dao.WishesRepository;
import gift.wishlist.entity.Wish;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class WishesRepositoryTest {

    @Autowired
    private WishesRepository wishesRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("위시 추가 및 ID 조회 테스트")
    void saveAndFindById() {
        Member member = new Member("test@email.com", "test");
        member = memberRepository.save(member);

        Product product = new Product("test", 1000, "test.jpg");
        product = productRepository.save(product);

        Wish wish = new Wish(member, product);
        Wish savedWish = wishesRepository.save(wish);

        Wish foundWish = wishesRepository.findById(savedWish.getId())
                .orElse(null);

        assertThat(foundWish).isNotNull();
        assertThat(foundWish.getMember()).isEqualTo(savedWish.getMember());
        assertThat(foundWish.getProduct()).isEqualTo(savedWish.getProduct());
    }

    @Test
    @DisplayName("위시 ID 조회 실패 테스트")
    void findByIdFailed() {
        Member member = new Member("test@email.com", "test");
        member = memberRepository.save(member);

        Product product = new Product("test", 1000, "test.jpg");
        product = productRepository.save(product);

        Wish wish = new Wish(member, product);
        wishesRepository.save(wish);

        Wish foundWish = wishesRepository.findById(123456789L)
                .orElse(null);

        assertThat(foundWish).isNull();
    }

    @Test
    @DisplayName("위시 회원 ID 조회 테스트")
    void findByMemberId() {
        Member member1 = new Member("test1@email.com", "test1");
        member1 = memberRepository.save(member1);

        Member member2 = new Member("test2@email.com", "test2");
        member2 = memberRepository.save(member2);

        Product product1 = new Product("product1", 1000, "test1.jpg");
        product1 = productRepository.save(product1);

        Product product2 = new Product("product2", 2000, "test2.jpg");
        product2 = productRepository.save(product2);

        Wish wish1 = new Wish(member1, product1);
        Wish wish2 = new Wish(member2, product2);
        Wish wish3 = new Wish(member1, product2);

        wishesRepository.save(wish1);
        wishesRepository.save(wish2);
        wishesRepository.save(wish3);

        Page<Wish> wishesPage = wishesRepository.findByMember_Id(member1.getId(), PageRequest.of(0, 10));

        assertThat(wishesPage.getTotalElements()).isEqualTo(2);
    }

    @Test
    @DisplayName("위시 회원 ID 조회 실패 테스트")
    void findByMemberIdFailed() {
        Member member1 = new Member("test1@email.com", "test1");
        member1 = memberRepository.save(member1);

        Member member2 = new Member("test2@email.com", "test2");
        member2 = memberRepository.save(member2);

        Product product1 = new Product("product1", 1000, "test1.jpg");
        product1 = productRepository.save(product1);

        Product product2 = new Product("product2", 2000, "test2.jpg");
        product2 = productRepository.save(product2);

        Wish wish1 = new Wish(member1, product1);
        Wish wish2 = new Wish(member2, product2);
        Wish wish3 = new Wish(member1, product2);

        wishesRepository.save(wish1);
        wishesRepository.save(wish2);
        wishesRepository.save(wish3);

        Page<Wish> wishPage = wishesRepository.findByMember_Id(987654321L, PageRequest.of(0, 10));

        assertThat(wishPage).isEmpty();
    }

    @Test
    @DisplayName("위시 삭제 테스트")
    void deleteWish() {
        Member member = new Member("test@email.com", "test");
        member = memberRepository.save(member);

        Product product = new Product("test", 1000, "test.jpg");
        product = productRepository.save(product);

        Wish wish = new Wish(member, product);
        Wish savedWish = wishesRepository.save(wish);

        wishesRepository.deleteById(savedWish.getId());

        boolean exists = wishesRepository.existsById(savedWish.getId());
        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("회원 ID 및 상품 ID로 위시 조회 테스트")
    void findByMember_IdAndProduct_Id() {
        Member member = new Member("test@email.com", "test");
        member = memberRepository.save(member);

        Product product = new Product("test", 1000, "test.jpg");
        product = productRepository.save(product);

        Wish wish = new Wish(member, product);
        wishesRepository.save(wish);

        Wish foundWish = wishesRepository.findByMember_IdAndProduct_Id(member.getId(), product.getId())
                .orElse(null);
        assertThat(foundWish).isNotNull();
        assertThat(foundWish.getMember()
                .getEmail()).isEqualTo(member.getEmail());
        assertThat(foundWish.getProduct()
                .getName()).isEqualTo(product.getName());
    }

    @Test
    @DisplayName("회원 ID 및 상품 ID로 위시 존재 여부 실패 확인 테스트")
    void findByMember_IdAndProduct_IdFailed() {
        Member member = new Member("test@email.com", "test");
        member = memberRepository.save(member);

        Product product = new Product("test", 1000, "test.jpg");
        product = productRepository.save(product);

        Wish wish = new Wish(member, product);
        wishesRepository.save(wish);

        Wish foundWish = wishesRepository.findByMember_IdAndProduct_Id(12345L, 67890L)
                .orElse(null);
        assertThat(foundWish).isNull();
    }
}