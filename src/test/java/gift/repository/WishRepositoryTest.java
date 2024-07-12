package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import gift.entity.MemberEntity;
import gift.entity.ProductEntity;
import gift.entity.WishEntity;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
        MemberEntity member = new MemberEntity("test", "password");
        MemberEntity savedMember = memberRepository.save(member);

        ProductEntity product1 = new ProductEntity("product1", 1000, "product1.jpg");
        ProductEntity product2 = new ProductEntity("product2", 2000, "product2.jpg");

        ProductEntity savedProduct1 = productRepository.save(product1);
        ProductEntity savedProduct2 = productRepository.save(product2);

        WishEntity wish1 = new WishEntity(savedMember, savedProduct1);
        WishEntity wish2 = new WishEntity(savedMember, savedProduct2);

        WishEntity expected1 = wishRepository.save(wish1);
        WishEntity expected2 = wishRepository.save(wish2);

        // when
        List<WishEntity> actualList = wishRepository.findAllByMemberEntity(savedMember);

        // then
        assertThat(actualList).isNotNull();
        assertThat(actualList).hasSize(2);
        assertThat(actualList).containsExactlyInAnyOrder(expected1, expected2);
    }

    @Test
    @DisplayName("findById 테스트")
    void findById(){
        // given
        MemberEntity member = new MemberEntity("test", "password");
        MemberEntity savedMember = memberRepository.save(member);

        ProductEntity product = new ProductEntity("product", 1000, "product1.jpg");
        ProductEntity savedProduct = productRepository.save(product);

        WishEntity request = new WishEntity(savedMember, savedProduct);
        WishEntity expected = wishRepository.save(request);

        // when
        WishEntity actual = wishRepository.findById(expected.getId()).orElseThrow();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("save 테스트")
    void save(){
        // given
        MemberEntity member = new MemberEntity("test", "password");
        MemberEntity savedMember = memberRepository.save(member);

        ProductEntity product = new ProductEntity("product", 1000, "product1.jpg");
        ProductEntity savedProduct = productRepository.save(product);

        WishEntity expected = new WishEntity(savedMember, savedProduct);

        // when
        WishEntity actual = wishRepository.save(expected);

        // then
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getMemberEntity()).isEqualTo(expected.getMemberEntity()),
            () -> assertThat(actual.getProductEntity()).isEqualTo(expected.getProductEntity())
        );
    }

    @Test
    @DisplayName("delete 테스트")
    void delete(){
        // given
        MemberEntity member = new MemberEntity("test", "password");
        MemberEntity savedMember = memberRepository.save(member);

        ProductEntity product = new ProductEntity("product", 1000, "product1.jpg");
        ProductEntity savedProduct = productRepository.save(product);

        WishEntity request = new WishEntity(savedMember, savedProduct);
        WishEntity savedWish = wishRepository.save(request);

        // when
        wishRepository.delete(savedWish);

        // then
        assertTrue(wishRepository.findById(savedWish.getId()).isEmpty());
    }
}