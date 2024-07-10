package gift;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import gift.domain.Member;
import gift.domain.Product;
import gift.domain.Wish;
import gift.repository.WishRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@DataJpaTest
public class WishRepositoryTest {
    @Autowired
    private WishRepository wishRepository;

    @Test
    @DisplayName("위시 추가 성공")
    void addWish() {
        Wish wish = new Wish(1L,1L);

        Wish savedWish = wishRepository.save(wish);
        Long wishMemberID = savedWish.getMemberId();

        List<Wish> addedWishes = wishRepository.findByMemberId(wishMemberID);
        Wish addedWish = addedWishes.get(0);

        assertThat(addedWish.getMemberId()).isEqualTo(1L);
        assertThat(addedWish.getProductId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("위시 삭제 성공")
    void deleteWish() {
        Wish wish = new Wish(1L,1L);
        Wish savedWish = wishRepository.save(wish);
        Long wishID = savedWish.getId();

        wishRepository.deleteById(wishID);

        boolean exists = wishRepository.existsById(wishID);
        assertThat(exists).isFalse();
    }
}
