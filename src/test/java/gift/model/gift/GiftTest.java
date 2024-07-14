package gift.model.gift;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GiftTest {

    @Test
    void checkThrowExceptionContainsKakao() {
        String invalidName = "카카오 문구";
        int price = 1000;
        String imageUrl = "test.jpg";

        assertThatThrownBy(() -> new Gift(invalidName, price, imageUrl))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("카카오 문구는 MD와 협의 후 사용가능합니다.");
    }

}