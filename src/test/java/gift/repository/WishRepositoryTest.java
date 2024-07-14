package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.model.Member;
import gift.model.Product;
import gift.model.Wish;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
public class WishRepositoryTest {

    @Autowired
    private WishRepository wishRepository;

    @BeforeEach
    public void setUp() {
        wishRepository.deleteAll();
    }

    @Test
    @DisplayName("위시리스트 항목 추가 및 ID로 조회")
    public void testSaveAndFindById() {
        Member member = new Member(1L, "test@example.com", "password");
        Product product = new Product(1L, "Product1", 100, "imageUrl1");

        Wish wish = new Wish(null, member, product);
        Wish savedWish = wishRepository.save(wish);
        Optional<Wish> foundWish = wishRepository.findById(savedWish.getId());

        assertThat(foundWish).isPresent();
        assertThat(foundWish.get().getMember().getId()).isEqualTo(1L);
        assertThat(foundWish.get().getProduct().getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("모든 위시리스트 항목 조회 (페이지네이션 적용)")
    public void testFindAllByMemberIdWithPagination() {
        Member member = new Member(1L, "test@example.com", "password");
        Product product1 = new Product(1L, "Product1", 100, "imageUrl1");
        Product product2 = new Product(2L, "Product2", 200, "imageUrl2");

        Wish wish1 = new Wish(null, member, product1);
        Wish wish2 = new Wish(null, member, product2);

        wishRepository.save(wish1);
        wishRepository.save(wish2);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Wish> wishPage = wishRepository.findAllByMemberId(1L, pageable);

        assertThat(wishPage.getTotalElements()).isEqualTo(2);
        assertThat(wishPage.getContent()).hasSize(
            (int) Math.min(2, pageable.getPageSize()));
    }

    @Test
    @DisplayName("위시리스트 항목 삭제")
    public void testDeleteById() {
        Member member = new Member(1L, "test@example.com", "password");
        Product product = new Product(1L, "Product1", 100, "imageUrl1");

        Wish wish = new Wish(null, member, product);
        Wish savedWish = wishRepository.save(wish);

        wishRepository.deleteById(savedWish.getId());
        Optional<Wish> foundWish = wishRepository.findById(savedWish.getId());

        assertThat(foundWish).isNotPresent();
    }

    @Test
    @DisplayName("회원 ID와 상품 ID로 위시리스트 항목 존재 여부 확인")
    public void testExistsByMemberIdAndProductId() {
        Member member = new Member(1L, "test@example.com", "password");
        Product product = new Product(1L, "Product1", 100, "imageUrl1");

        Wish wish = new Wish(null, member, product);
        wishRepository.save(wish);

        boolean exists = wishRepository.existsByMemberIdAndProductId(1L, 1L);
        assertThat(exists).isTrue();
    }
}
