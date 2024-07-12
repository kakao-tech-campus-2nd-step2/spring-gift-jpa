package gift.service;

import gift.model.Product;
import gift.model.WishlistDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Set;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("product가 삭제되었을 때 wishlist에서 해당 product가 삭제되어야 함")
    void productDeleteCascadeWishlistTest() {
        // given
        String testEmail = "test@gmail.com";
        Product product = productService.save(new Product("test", 123, "test.com"));
        wishlistService.addWishlistProduct(testEmail, new WishlistDTO(product.getId()));

        // when
        productService.delete(product.getId());

        // then
        Page<Product> products = wishlistService.getWishlistProducts(testEmail, PageRequest.of(0, 5));
        Assertions.assertThat(products).hasSize(1);
    }
}
