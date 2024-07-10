package gift.repository;

import gift.model.Wish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class WishRepositoryTest {
    @Autowired
    private WishRepository wishs;

    @BeforeEach
    void setUp(){
        wishs.deleteAll();
        wishs.save(new Wish(1L, 1L));
        wishs.save(new Wish(1L, 2L));
        wishs.save(new Wish(2L,1L));
    }

    @DisplayName("whis 저장")
    @Test
    void save(){
        Wish expected = new Wish(1l, 1l );
        Wish actual = wishs.save(expected);
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("해당 memberId를 가진 Wishlist 반환")
    @Test
    void getWishsbyMemberId(){
        List<Wish> actual = wishs.findByMemberId(1L);
        List<Wish> expected = List.of(new Wish(1L, 1L),
                new Wish(1L, 2L));
        assertThat(actual).isEqualTo(expected);
    }
}
