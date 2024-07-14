package gift.repository;

import gift.model.Member;
import gift.model.Product;
import gift.model.Wish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class WishRepositoryTest {
    @Autowired
    private WishRepository wishs;

    @Autowired
    private MemberRepository members;

    @Autowired
    private ProductRepository products;

    @DisplayName("wish 저장")
    @Test
    void save(){
        members.save(new Member("test.gamil.com", "test1234"));
        products.save(new Product("Product1", 1000, "1.img"));
        Member member = members.findByEmail("test.gamil.com").orElseThrow();
        Product product = products.findByName("Product1").orElseThrow();
        Wish expected = new Wish(member, product);
        Wish actual = wishs.save(expected);
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("해당 memberId를 가진 Wishlist 반환")
    @Test
    void getWishsbyMemberId(){
        members.save(new Member("test.gamil.com", "test1234"));
        products.save(new Product("Product1", 1000, "1.img"));
        products.save(new Product("Product2", 5000, "2.img"));
        Member member = members.findByEmail("test.gamil.com").orElseThrow();
        Product product1 = products.findByName("Product1").orElseThrow();
        Product product2 = products.findByName("Product2").orElseThrow();
        Wish wish1 = new Wish(member, product1);
        Wish wish2 = new Wish(member, product2);
        wishs.save(wish1);
        wishs.save(wish2);
        List<Wish> actual = wishs.findByMember_Id(1L);
        List<Wish> expected = List.of(wish1, wish2);
        assertThat(actual).isEqualTo(expected);
    }
}
