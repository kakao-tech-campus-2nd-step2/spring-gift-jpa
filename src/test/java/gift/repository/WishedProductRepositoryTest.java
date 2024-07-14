package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.domain.Member;
import gift.domain.Product;
import gift.domain.WishedProduct;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
public class WishedProductRepositoryTest {

    @Autowired
    private WishedProductRepository wishedProductRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProductRepository productRepository;

    private Member member;
    private Product product;
    private WishedProduct wishedProduct;

    @BeforeEach
    void setup() {
        member = new Member("admin@gmail.com", "admin");
        memberRepository.save(member);
        product = new Product("test", 1000, "testImage");
        productRepository.save(product);
        wishedProduct = new WishedProduct(member, product, 3);
    }

    @DisplayName("위시리스트 상품 추가")
    @Test
    void save() {
        // given
        WishedProduct expected = wishedProduct;

        // when
        WishedProduct actual = wishedProductRepository.save(expected);

        // then
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getMemberEmail()).isEqualTo(expected.getMemberEmail()),
            () -> assertThat(actual.getProductId()).isEqualTo(expected.getProductId()),
            () -> assertThat(actual.getAmount()).isEqualTo(expected.getAmount())
        );
    }

    @DisplayName("한 회원의 위시리스트 조회")
    @Test
    void findByMemberEmail() {
        // given
        WishedProduct savedWishedProduct = wishedProductRepository.save(wishedProduct);
        List<WishedProduct> expected = new ArrayList<>(List.of(savedWishedProduct));
        Pageable pageable = PageRequest.of(0, 5);

        // when
        List<WishedProduct> actual = wishedProductRepository.findByMember(member, pageable)
            .stream().toList();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("위시리스트 상품 삭제")
    @Test
    void delete() {
        // given
        long id = wishedProductRepository.save(wishedProduct).getId();

        // when
        wishedProductRepository.deleteById(id);

        // then
        assertThat(wishedProductRepository.findById(id)).isEmpty();
    }

    @DisplayName("위시리스트 상품 수량 변경")
    @Test
    void update() {
        // given
        WishedProduct expected = wishedProductRepository.save(wishedProduct);
        expected.setAmount(5);

        // when
        WishedProduct actual = wishedProductRepository.save(expected);

        // then
        assertAll(
            () -> assertThat(actual.getId()).isEqualTo(expected.getId()),
            () -> assertThat(actual.getMemberEmail()).isEqualTo(expected.getMemberEmail()),
            () -> assertThat(actual.getProductId()).isEqualTo(expected.getProductId()),
            () -> assertThat(actual.getAmount()).isEqualTo(expected.getAmount())
        );
    }
}
