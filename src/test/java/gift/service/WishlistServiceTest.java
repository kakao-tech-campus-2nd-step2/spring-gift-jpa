package gift.service;

import gift.model.Product;
import gift.model.WishlistDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WishlistServiceTest {

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("wishlist가 삭제되었을 때 product가 삭제되지 않아야 함")
    void wishlistDeleteCascadeProductTest() {
        // given
        String testEmail = "test@gmail.com";
        Product product = productService.save(new Product("test", 123, "test.com"));
        wishlistService.addWishlistProduct(testEmail, new WishlistDTO(product.getId()));

        // when
        wishlistService.deleteWishlist(testEmail);

        // then
        product = productService.findById(product.getId());
        Assertions.assertThat(product).isNotNull();
    }
}
