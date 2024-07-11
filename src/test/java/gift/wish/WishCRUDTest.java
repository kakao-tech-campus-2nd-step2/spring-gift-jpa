package gift.wish;

import gift.member.model.Member;
import gift.member.repository.MemberRepository;
import gift.product.model.Product;
import gift.product.repository.ProductRepository;
import gift.wish.model.Wish;
import gift.wish.model.WishDTO;
import gift.wish.repository.WishRepository;
import gift.wish.service.WishService;
import gift.wish.service.WishServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(WishServiceImpl.class)
@Transactional
public class WishCRUDTest {

    @Autowired
    private WishService wishService;

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    private Member testMember;
    private Product testProduct;
    private Wish testWish;

    @BeforeEach
    public void setUp() {
        // Given
        testMember = new Member("test@example.com", "password");
        memberRepository.save(testMember);

        testProduct = new Product("Ice Americano", 2000, "http://example.com/example.jpg");
        productRepository.save(testProduct);

        testWish = new Wish(testMember, testProduct);
        wishRepository.save(testWish);
    }

    @Test
    public void testAddProductToWishlist() {
        // Given
        Product newProduct = new Product("CaffeLatte", 2500, "http://example.com/image2.jpg");
        productRepository.save(newProduct);

        // When
        wishService.addProductToWishlist(testMember, newProduct.getId());

        // Then
        List<Wish> wishes = wishRepository.findAllByMember(testMember);
        assertThat(wishes).hasSize(2);
        assertThat(wishes).extracting(wish -> wish.getProduct().getName()).contains("CaffeLatte");
    }

    @Test
    public void testGetWishlistByMemberId() {
        // When
        List<WishDTO> wishlist = wishService.getWishlistByMemberId(testMember);

        // Then
        assertThat(wishlist).hasSize(1);
        assertThat(wishlist.get(0).getProductName()).isEqualTo("Ice Americano");
    }

    @Test
    public void testDeleteWish() {
        // Given
        Long wishId = testWish.getId();

        // When
        wishService.deleteWish(wishId);

        // Then
        List<Wish> wishes = wishRepository.findAllByMember(testMember);
        assertThat(wishes).isEmpty();
    }
}
