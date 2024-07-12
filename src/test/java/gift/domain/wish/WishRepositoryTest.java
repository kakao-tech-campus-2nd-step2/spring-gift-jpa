package gift.domain.wish;

import gift.domain.member.Member;
import gift.domain.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class WishRepositoryTest {

    @Autowired
    private WishRepository wishRepository;

    @BeforeEach
    void setUp() {
        Member member = new Member("dummy@gmail.com","더미","password",1);
        member.setId(1L);

        Product product = new Product(1L,"커피",10000,"test.jpg");

        Wish wish = new Wish(1L,1L,10);
        wishRepository.save(wish);
    }

    @Test
    void findAllByMemberId() {
        //given
        Wish expected = new Wish(1L,1L,10);

        //when
        List<Wish> actual = wishRepository.findAllByMemberId(1L);

        //then
        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual)
                        .anyMatch(wish -> Objects.equals(wish.getMemberId(), expected.getMemberId())
                                && Objects.equals(wish.getProductId(), expected.getProductId())
                                && Objects.equals(wish.getQuantity(), expected.getQuantity()))
        );
    }

    @Test
    void findDistinctByMemberIdAndProductId() {
        //given
        Wish expected = new Wish(1L,1L,10);

        //when
        Wish actual = wishRepository.findDistinctByMemberIdAndProductId(1L,1L).orElse(null);

        //then
        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual.getQuantity()).isEqualTo(expected.getQuantity())
        );
    }
}
