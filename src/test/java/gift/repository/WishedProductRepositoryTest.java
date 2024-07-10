package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.domain.WishedProduct;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class WishedProductRepositoryTest {

    @Autowired
    private WishedProductRepository wishedProductRepository;
    private MemberRepository memberRepository;
    private ProductRepository productRepository;

    @DisplayName("위시리스트 상품 추가")
    @Test
    void save() {
        // given
        WishedProduct expected = new WishedProduct("admin@gmail.com", 1L, 3);

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
        String memberEmail = "admin@gmail.com";
        WishedProduct wishedProduct1 = new WishedProduct(memberEmail, 1L, 3);
        WishedProduct wishedProduct2 = new WishedProduct(memberEmail, 2L, 7);
        wishedProduct1 = wishedProductRepository.save(wishedProduct1);
        wishedProduct2 = wishedProductRepository.save(wishedProduct2);
        List<WishedProduct> expected = new ArrayList<>(List.of(wishedProduct1, wishedProduct2));

        // when
        List<WishedProduct> actual = wishedProductRepository.findByMemberEmail(memberEmail);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("위시리스트 상품 삭제")
    @Test
    void delete() {
        // given
        WishedProduct wishedProduct = new WishedProduct("admin@gmail.com", 1L, 3);
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
        WishedProduct wishedProduct = new WishedProduct("admin@gmail.com", 1L, 3);
        long id = wishedProductRepository.save(wishedProduct).getId();
        WishedProduct expected = new WishedProduct(id, "admin@gmail.com", 1L, 5);

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
