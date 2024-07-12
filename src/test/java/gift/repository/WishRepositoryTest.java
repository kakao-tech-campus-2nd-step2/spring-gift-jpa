package gift.repository;

import gift.entity.Member;
import gift.entity.Product;
import gift.entity.Wish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class WishRepositoryTest {

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member testMember;
    private Product testProduct1;
    private Product testProduct2;

    @BeforeEach
    void setUp() {
        testMember = new Member("test@email.com", "password");
        memberRepository.save(testMember);

        testProduct1 = new Product("almond", 500, "almond.jpg");
        testProduct2 = new Product("ice", 9000, "ice.jpg");
        productRepository.save(testProduct1);
        productRepository.save(testProduct2);
    }

    @Test
    @DisplayName("멤버의 전체 위시 찾기")
    void findAllByMemberIdWithProduct() {
        //Given
        Wish testWish1 = new Wish(testMember, 100, testProduct1);
        Wish testWish2 = new Wish(testMember, 2000, testProduct2);
        wishRepository.save(testWish1);
        wishRepository.save(testWish2);

        //When
        PageRequest pageable = PageRequest.of(0, 10);
        List<Wish> wishes = wishRepository.findAllByMember(testMember, pageable).getContent();

        //Then
        assertThat(wishes).isNotEmpty()
                .hasSize(2)
                .containsExactly(testWish1, testWish2);

        assertThat(testWish1.getProduct()).isEqualTo(testProduct1);
    }

    @Test
    @DisplayName("멤버, 상품으로 위시 찾기")
    void findByMemberIdAndProductId() {
        //Given
        Wish testWish1 = new Wish(testMember, 100, testProduct1);
        wishRepository.save(testWish1);

        //When
        Optional<Wish> wish = wishRepository.findByMemberAndProduct(testMember, testProduct1);

        //Then
        assertThat(wish).isPresent()
                .hasValueSatisfying(w -> {
                    assertThat(w).isEqualTo(testWish1);
                    assertThat(w.getProduct()).isEqualTo(testProduct1);
                });
    }

}
