package gift.service;

import gift.entity.Product;
import gift.entity.WishlistDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class WishlistServiceTest {

    @Autowired
    private WishlistService wishlistService;
    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("삭제를 원하는 product가 내 wishlist에서 삭제되어야 함")
    void wishlistDeleteCascadeProductTest() {
        // given
        String testEmail = "test@gmail.com";
        Product product1 = productService.save(new Product("test1", 123, "test.com"));
        Product product2 = productService.save(new Product("test2", 123, "test.com"));
        wishlistService.addWishlistProduct(testEmail, new WishlistDTO(product1.getId()));
        wishlistService.addWishlistProduct(testEmail, new WishlistDTO(product2.getId()));

        // when
        wishlistService.deleteWishlist(testEmail, product1.getId());

        // then
        List<Product> products = wishlistService.getWishlistProducts(testEmail, PageRequest.of(0, 10)).getContent();
        Assertions.assertThat(products.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("다수의 유저가 위시리스트를 만든 경우 테스트")
    void multiUserWishlistTest() {
        // given
        String testEmail1 = "test1@gmail.com";
        String testEmail2 = "test2@gmail.com";
        Product product1 = productService.save(new Product("test1", 123, "test.com"));
        Product product2 = productService.save(new Product("test2", 123, "test.com"));
        Product product3 = productService.save(new Product("test3", 123, "test.com"));

        // when
        wishlistService.addWishlistProduct(testEmail1, new WishlistDTO(product1.getId()));
        wishlistService.addWishlistProduct(testEmail1, new WishlistDTO(product2.getId()));

        wishlistService.addWishlistProduct(testEmail2, new WishlistDTO(product2.getId()));
        wishlistService.addWishlistProduct(testEmail2, new WishlistDTO(product3.getId()));

        // then
        List<Product> products1 = wishlistService.getWishlistProducts(testEmail1, PageRequest.of(0, 10)).getContent();
        List<Product> products2 = wishlistService.getWishlistProducts(testEmail2, PageRequest.of(0, 10)).getContent();

        Assertions.assertThat(products1.get(0).getName()).isEqualTo("test1");
        Assertions.assertThat(products1.get(1).getName()).isEqualTo("test2");

        Assertions.assertThat(products2.get(0).getName()).isEqualTo("test2");
        Assertions.assertThat(products2.get(1).getName()).isEqualTo("test3");
    }
}
