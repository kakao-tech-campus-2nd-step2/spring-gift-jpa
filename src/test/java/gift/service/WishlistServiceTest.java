package gift.service;


import gift.model.Product;
import gift.model.User;
import gift.model.WishlistItem;
import gift.repository.ProductRepository;
import gift.repository.UserRepository;
import gift.repository.WishlistRepository;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class WishlistServiceTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WishlistService wishlistService;

    @BeforeEach
    public void setUp(){
        User user1 = new User(1L, "user1@naver.com", "pass1");
        userRepository.save(user1);

        Product product1 = new Product(1L, "water", 1000L, "www.naver.com");
        Product product2 = new Product(2L, "cola", 3000L, "www.coke.com");
        Product product3 = new Product(3L, "bread", 5000L, "www.bread.com");
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);

        WishlistItem wishlistItem1 = new WishlistItem(1L, user1, product1, 3);
        WishlistItem wishlistItem2 = new WishlistItem(2L, user1, product2, 5);
        wishlistRepository.save(wishlistItem1);
        wishlistRepository.save(wishlistItem2);
    }
    @Test
    @Rollback(false)
    @Transactional
    public void testGetWishlistByUserId() {
        // given
        List<WishlistItem> wishlistItemList = wishlistRepository.findListByUserId(1L);

        // when
        Page<WishlistItem> wishlistPage = wishlistService.getWishlistByUserId(1L, PageRequest.of(0, 10));

        // then
        List<WishlistItem> wishlistItems = wishlistPage.getContent();
        Assertions.assertThat(wishlistItemList).usingRecursiveComparison().isEqualTo(wishlistItems);
    }

    @Test
    @Transactional
    @DisplayName("상품 추가했을 때 amount 더하기 정확히 되었는지")
    public void testSaveWishlistItemsWithUserId(){
        //given
        List<WishlistItem> addItemList = new ArrayList<>();
        WishlistItem addItem1 = new WishlistItem(0L,
            userRepository.findById(1L).get(),
            productRepository.findById(2L).get(),
            3);
        addItemList.add(addItem1);

        List<WishlistItem> expected = new ArrayList<>();
        expected.add(new WishlistItem(1L,
            userRepository.findById(1L).get(),
            productRepository.findById(1L).get(),
            3)); // productId가 1인 항목은 변경 없음
        expected.add(new WishlistItem(2L,
            userRepository.findById(1L).get(),
            productRepository.findById(2L).get(),
            8));

        // when
        wishlistService.saveWishlistItemsWithUserId(1L, addItemList);

        // then
        Page<WishlistItem> wishlistPage = wishlistService.getWishlistByUserId(1L, PageRequest.of(0, 10));
        List<WishlistItem> actual = wishlistPage.getContent();
        System.out.println(actual.get(0));
        System.out.println(actual.get(1));
        System.out.println(expected.get(0));
        System.out.println(expected.get(1));
        Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}
