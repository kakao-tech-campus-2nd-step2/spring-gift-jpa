package gift.service;

import gift.entity.Product;
import gift.entity.Wishlist;
import gift.entity.WishlistDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class ProductServiceTest {

    @Autowired
    private ProductService productService;
    @Autowired
    private WishlistService wishlistService;

    @Test
    @DisplayName("product가 삭제되었을 때 product_wishlist에서 해당 행이 삭제되어야 함")
    void productDeleteCascadeWishlistTest() {
        // given
        String testEmail = "test@gmail.com";
        Product product = productService.save(new Product("test", 123, "test.com"));
        wishlistService.addWishlistProduct(testEmail, new WishlistDTO(product.getId()));

        // when
        productService.delete(product.getId());

        // then
        Page<Product> products = wishlistService.getWishlistProducts(testEmail, PageRequest.of(0, 10));
        Assertions.assertThat(products).hasSize(0);
    }

    @Test
    @DisplayName("특정 product를 wishlist에 담은 유저의 수 확인 테스트")
    void productWishlistCheckTest() {
        // given
        String testEmail1 = "test1@gmail.com";
        String testEmail2 = "test2@gmail.com";
        Product product = productService.save(new Product("test", 123, "test.com"));

        // when
        wishlistService.addWishlistProduct(testEmail1, new WishlistDTO(product.getId()));
        wishlistService.addWishlistProduct(testEmail2, new WishlistDTO(product.getId()));

        // then
        List<Wishlist> wishlists = productService.getProductWishlist(product.getId());
        Assertions.assertThat(wishlists).hasSize(2);
    }
}
