package gift.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberTest {
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
    @DisplayName("멤버의 위시리스트에 위시 추가 테스트")
    void addWish() {
        // when
        member.addWish(wish);

        // then
        List<Wish> wishes = member.getWishes();
        assertAll(
                ()->assertThat(wishes).contains(wish),
                ()->assertThat(wish.getMember()).isEqualTo(member)
        );
    }

    @Test
    @DisplayName("멤버의 위시리스트에서 위시 삭제 테스트")
    void removeWish() {
        // when
        member.removeWish(wish);

        // then
        List<Wish> wishes = member.getWishes();
        assertAll(
                ()->assertThat(wishes.size()).isEqualTo(0),
                ()->assertThat(wish.getMember()).isNull()
        );
    }
}