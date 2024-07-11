package gift.repository;

import gift.product.repository.MemberRepository;
import gift.product.repository.ProductRepository;
import gift.product.repository.WishListRepository;
import gift.product.model.Member;
import gift.product.model.Product;
import gift.product.model.Wish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class WishListRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private WishListRepository wishListRepository;

    private Product originProduct;
    private Member originMember;

    @BeforeEach
    void setUp() {
        originProduct = productRepository.save(new Product("product", 1000, "image.url"));
        originMember = memberRepository.save(new Member("user@email.com", "1234"));
    }

    @Test
    void testRegisterWishList() {
        Wish product = new Wish(originMember, originProduct);
        wishListRepository.save(product);
    }

    @Test
    void testDeleteWishList() {
        Wish wish = wishListRepository.save(new Wish(originMember, originProduct));
        wishListRepository.deleteById(wish.getId());
    }

}
