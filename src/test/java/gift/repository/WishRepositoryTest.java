package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.model.Member;
import gift.model.Product;
import gift.model.Wish;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@DataJpaTest
class WishRepositoryTest {

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void save() {
        Member expectedMember = saveMember("member1@example.com", "password1", "member1", "user");
        Product expectedProduct = saveProduct("gamza", 500, "gamza.jpg");
        Wish expected = createWish(expectedMember, expectedProduct);

        Wish actual = wishRepository.save(expected);

        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getMember().getId()).isEqualTo(expectedMember.getId()),
            () -> assertThat(actual.getProduct().getId()).isEqualTo(expectedProduct.getId()),
            () -> assertThat(actual.getCount()).isEqualTo(expected.getCount())
        );
    }

    @Test
    void findAllByMemberId() {
        Member expectedMember = saveMember("member1@example.com", "password1", "member1", "user");
        Product expectedProduct1 = saveProduct("gamza", 500, "gamza.jpg");
        Wish expected1 = createWish(expectedMember, expectedProduct1);
        Product expectedProduct2 = saveProduct("goguma", 1500, "goguma.jpg");
        Wish expected2 = createWish(expectedMember, expectedProduct2);
        wishRepository.save(expected1);
        wishRepository.save(expected2);

        List<Wish> actual = wishRepository.findAllByMemberId(expectedMember.getId(),
            PageRequest.of(0, 10, Sort.by(
                Direction.ASC, "product"))).getContent();

        assertThat(actual).containsExactly(expected1, expected2);
    }

    @Test
    void findByMemberIdAndProductId() {
        Member expectedMember = saveMember("member1@example.com", "password1", "member1", "user");
        Product expectedProduct = saveProduct("gamza", 500, "gamza.jpg");
        Wish expected = createWish(expectedMember, expectedProduct);

        wishRepository.save(expected);

        Wish actual = wishRepository.findByMemberIdAndProductId(expectedMember.getId(),
            expectedProduct.getId());

        assertAll(
            () -> assertThat(actual.getMember().getId()).isEqualTo(expectedMember.getId()),
            () -> assertThat(actual.getMember().getRole()).isEqualTo(expectedMember.getRole()),
            () -> assertThat(actual.getProduct().getId()).isEqualTo(expectedProduct.getId()),
            () -> assertThat(actual.getProduct().getPrice()).isEqualTo(expectedProduct.getPrice()),
            () -> assertThat(actual.getCount()).isEqualTo(expected.getCount())
        );
    }

    @Test
    void deleteByMemberIdAndProductId() {
        Member expectedMember = saveMember("member1@example.com", "password1", "member1", "user");
        Product expectedProduct = saveProduct("gamza", 500, "gamza.jpg");
        Wish expected = createWish(expectedMember, expectedProduct);
        wishRepository.save(expected);

        wishRepository.deleteByMemberIdAndProductId(expected.getMember().getId(),
            expected.getProduct().getId());

        Wish actual = wishRepository.findByMemberIdAndProductId(expected.getMember().getId(),
            expected.getProduct().getId());
        assertThat(actual).isNull();
    }

    private Product saveProduct(String name, Integer price, String imageUrl) {
        var product = new Product(name, price, imageUrl);
        return productRepository.save(product);
    }

    private Member saveMember(String email, String password, String name, String role) {
        var member = new Member(email, password, name, role);
        return memberRepository.save(member);
    }

    private Wish createWish(Member expectedMember, Product expectedProduct) {
        return new Wish(
            expectedMember, expectedProduct, 1);
    }
}
