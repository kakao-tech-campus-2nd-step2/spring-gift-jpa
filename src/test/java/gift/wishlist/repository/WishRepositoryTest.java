package gift.wishlist.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.member.model.Member;
import gift.member.repository.MemberRepository;
import gift.product.model.Product;
import gift.product.repository.ProductRepository;
import gift.wishlist.model.Wish;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@DataJpaTest
class WishRepositoryTest {
    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager em;

    private Member member;
    private Product product;

    @BeforeEach
    void setUp() {
        member = new Member();
        member.setPassword("password");
        member.setEmail("user@example.com");
        member = memberRepository.save(member);

        product = new Product("product", 5000, "http://example.com/image1.jpg");
        product = productRepository.save(product);

        em.flush();
        em.clear();
    }

    @Test
    void testFindByMemberIdAndProductId() {
        Wish wish = new Wish(member, product);
        wishRepository.save(wish);

        em.flush();
        em.clear();

        List<Wish> wishList = wishRepository.findByMemberIdAndProductId(member.getId(), product.getId());
        assertThat(wishList).hasSize(1);
        assertThat(wishList.get(0).getMember().getId()).isEqualTo(member.getId());
        assertThat(wishList.get(0).getProduct().getId()).isEqualTo(product.getId());
    }

    @Test
    void testFindByMemberId() {
        Product product2 = new Product("product2", 2000, "http://example.com/image2.jpg");
        product2 = productRepository.save(product2);

        Wish wish1 = new Wish(member, product);
        Wish wish2 = new Wish(member, product2);
        wishRepository.save(wish1);
        wishRepository.save(wish2);

        List<Wish> wishList = wishRepository.findByMemberId(member.getId());
        assertThat(wishList).hasSize(2);
    }

    @Test
    void testSaveWish() {
        Wish wish = new Wish(member, product);
        Wish saveWish = wishRepository.save(wish);

        assertThat(saveWish).isNotNull();
        assertThat(saveWish.getId()).isNotNull();
        assertThat(saveWish.getMember().getId()).isEqualTo(member.getId());
        assertThat(saveWish.getProduct().getId()).isEqualTo(product.getId());
    }

    @Test
    void testDeleteByMemberIdAndProductId() {
        Wish wish = new Wish(member, product);
        wishRepository.save(wish);

        wishRepository.delete(wish);

        List<Wish> wishList = wishRepository.findByMemberIdAndProductId(member.getId(), product.getId());
        assertThat(wishList).isEmpty();
    }

    @Test
    void testExistsById() {
        Wish wish = new Wish(member, product);
        Wish saveWish = wishRepository.save(wish);

        boolean exists = wishRepository.existsById(saveWish.getId());
        assertThat(exists).isTrue();
    }

    @Test
    @Transactional
    void testDeleteById() {
        Wish wish = new Wish(member, product);
        Wish saveWish = wishRepository.save(wish);

        wishRepository.deleteById(saveWish.getId());

        em.flush();
        em.clear();

        Optional<Wish> wishList = wishRepository.findById(saveWish.getId());
        assertThat(wishList).isEmpty();
    }

    @Test
    void testDeleteByProductId() {
        Wish wish = new Wish(member, product);
        wishRepository.save(wish);

        wishRepository.delete(wish);

        List<Wish> wishList = wishRepository.findByMemberIdAndProductId(member.getId(), product.getId());
        assertThat(wishList).isEmpty();
    }

    @Test
    @Transactional
    void testUpdateWish() {
        Wish wish = new Wish(member, product);
        wishRepository.save(wish);

        Product newProduct = new Product("newProduct", 6000, "http://example.com/image2.jpg");
        newProduct = productRepository.save(newProduct);

        wish.setProduct(newProduct);
        Wish updatedWish = wishRepository.save(wish);

        em.flush();
        em.clear();

        Optional<Wish> fetchedWish = wishRepository.findById(updatedWish.getId());
        assertThat(fetchedWish).isPresent();
        assertThat(fetchedWish.get().getProduct().getName()).isEqualTo("newProduct");
        assertThat(fetchedWish.get().getProduct().getPrice()).isEqualTo(6000);
    }

    @Test
    void testFindByMemberIdWithPagination() {
        Wish wish = new Wish(member, product);
        wishRepository.save(wish);

        for (int i = 1; i < 21; i++) {
            Product newProduct = new Product("product" + i, 5000 + i, "http://example.com/image" + i + ".jpg");
            newProduct = productRepository.save(newProduct);
            Wish wish2 = new Wish(member, newProduct);
            wishRepository.save(wish2);
        }

        em.flush();
        em.clear();

        Pageable pageable = PageRequest.of(0, 5, Sort.by("id").ascending());

        Page<Wish> wishPage = wishRepository.findByMemberId(member.getId(), pageable);

        assertThat(wishPage).isNotNull();
        assertThat(wishPage.getContent()).hasSize(5);
        assertThat(wishPage.getTotalPages()).isEqualTo(5);
        assertThat(wishPage.getTotalElements()).isEqualTo(21);
        assertThat(wishPage.getContent().get(0).getMember().getId()).isEqualTo(member.getId());
    }
}