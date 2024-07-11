package gift.repository;

import gift.entity.Member;
import gift.entity.Product;
import gift.entity.Wish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
    private Product testProduct;

    @BeforeEach
    void setUp() {
        // Member 엔티티 생성 및 저장
        testMember = new Member("test@email.com", "password");
        memberRepository.save(testMember);

        // Product 엔티티 생성 및 저장
        testProduct = new Product("almond", 500, "almond.jpg");
        productRepository.save(testProduct);

        // Wish 엔티티 생성 및 저장
        Wish wish = new Wish(testMember, 100, testProduct);
        wishRepository.save(wish);
    }

    @Test
    void findAllByMemberIdWithProduct() {
        //When
        List<Wish> wishes = wishRepository.findAllByMemberIdWithProduct(testMember.getId());

        //Then
        assertThat(wishes).isNotEmpty();
        assertThat(wishes.get(0).getProduct().getName()).isEqualTo("almond");
    }

    @Test
    void findByMemberIdAndProductId() {
        //When
        Optional<Wish> wish = wishRepository.findByMemberIdAndProductId(testMember.getId(), testProduct.getId());

        //Then
        assertThat(wish).isPresent();
        assertThat(wish.get().getProduct().getName()).isEqualTo("almond");
    }

}
