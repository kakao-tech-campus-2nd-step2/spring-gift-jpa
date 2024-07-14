package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.common.dto.PageResponse;
import gift.model.product.ProductRequest;
import gift.model.product.ProductResponse;
import gift.model.user.UserRequest;
import gift.model.user.UserResponse;
import gift.model.wish.WishRequest;
import gift.model.wish.WishResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql("/truncate.sql")
public class WishServiceTest {

    @Autowired
    private WishService wishService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("위시 리스트 등록")
    void register() {
        UserRequest userRequest = new UserRequest("yso3865", "yso8296@gmail.com");
        ProductRequest productRequest = new ProductRequest("product1", 1000, "image1.jpg");
        UserResponse user = userService.register(userRequest);
        ProductResponse product = productService.register(productRequest);

        wishService.addWistList(user.id(), new WishRequest(product.id(), 3));
        PageResponse<WishResponse> wishes = wishService.findAllWish(user.id(), 1, 10);
        WishResponse actual = wishes.responses().get(0);

        assertAll(
            () -> assertThat(actual.wishId()).isNotNull(),
            () -> assertThat(actual.productId()).isNotNull(),
            () -> assertThat(actual.productName()).isEqualTo(product.name()),
            () -> assertThat(actual.count()).isEqualTo(3),
            () -> assertThat(actual.imageUrl()).isEqualTo(product.imageUrl()),
            () -> assertThat(actual.price()).isEqualTo(product.price())
        );
    }

    @Test
    @DisplayName("위시 리스트 조회")
    void findWish() {
        UserRequest userRequest = new UserRequest("yso3865", "yso8296@gmail.com");
        ProductRequest productRequest = new ProductRequest("product1", 1000, "image1.jpg");
        UserResponse user = userService.register(userRequest);
        ProductResponse product = productService.register(productRequest);
        wishService.addWistList(user.id(), new WishRequest(product.id(), 3));

        PageResponse<WishResponse> wishes = wishService.findAllWish(user.id(), 1, 10);

        assertThat(wishes).isNotNull();
    }

    @Test
    @DisplayName("위시 리스트 삭졔")
    void delete() {
        UserRequest userRequest = new UserRequest("yso3865", "yso8296@gmail.com");
        ProductRequest productRequest = new ProductRequest("product1", 1000, "image1.jpg");
        UserResponse user = userService.register(userRequest);
        ProductResponse product = productService.register(productRequest);
        wishService.addWistList(user.id(), new WishRequest(product.id(), 3));

        wishService.deleteWishList(user.id(), product.id());
        PageResponse<WishResponse> wishes = wishService.findAllWish(user.id(), 1, 10);

        assertThat(wishes.responses()).isEmpty();
    }
}
