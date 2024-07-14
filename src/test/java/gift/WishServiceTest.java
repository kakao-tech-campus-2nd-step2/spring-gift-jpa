package gift;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.member.Member;
import gift.member.MemberRepository;
import gift.product.Product;
import gift.product.ProductRepository;
import gift.wishes.Wish;
import gift.wishes.WishService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

@SpringBootTest
public class WishServiceTest {

    @Autowired
    private WishService wishService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProductRepository productRepository;

    @Test
    void pageTest(){
        Member member = new Member(null, "user", "user@gmail.com", "1234");
        Member newMember = memberRepository.save(member);

        for(int i = 0;i<100;i++){
            Product product = new Product(null, "Product" + i, 1234, null);
            Product newProduct = productRepository.save(product);
            wishService.createWish(newMember.getId(), newProduct.getId(), 1L);
        }

        Page<Wish> wishPage = wishService.getWishPage(newMember.getId(), 0);
        assertAll(
            () -> assertThat(wishPage.getTotalPages()).isEqualTo(10),
            () -> assertThat(wishPage.getTotalElements()).isEqualTo(100),
            () -> assertThat(wishPage.getSize()).isEqualTo(10)
        );
    }
}
