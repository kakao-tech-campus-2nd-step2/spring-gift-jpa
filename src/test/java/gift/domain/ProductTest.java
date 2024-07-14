package gift.domain;

import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    private Product product;
    private Member member;
    private Wish wish;
    @BeforeEach
    void setUp(){
        product = new Product("상품1",1000,"http://product1");
        member = new Member("a@a.com","1234");
        wish = new Wish(member,product,1);
    }

    @Test
    @DisplayName("상품의 위시리스트에 위시 추가 테스트")
    void addWish() {
        // when
        product.addWish(wish);

        // then
        List<Wish> wishes = product.getWishes();
        assertAll(
                ()->assertThat(wishes).contains(wish),
                ()->assertThat(wish.getProduct()).isEqualTo(product)
        );
    }

    @Test
    @DisplayName("상품의 위시리스트에서 위시 삭제 테스트")
    void removeWish() {
        // given
        product.addWish(wish);

        // when
        product.removeWish(wish);

        // then
        List<Wish> wishes = product.getWishes();
        assertAll(
                ()->assertThat(wishes.size()).isEqualTo(0),
                ()->assertThat(wish.getProduct()).isNull()
        );
    }
}