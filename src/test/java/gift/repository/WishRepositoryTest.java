package gift.repository;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import gift.entity.Wish;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class WishRepositoryTest {

    @Autowired
    private WishRepository wishRepository;

    @Test
    void save(){
        //given
        Wish expected = new Wish(1L,1L);

        //when
        Wish actual = wishRepository.save(expected);

        //then
        assertAll(
            ()->assertThat(actual.getId()).isNotNull(),
            ()->assertThat(actual.getMemberId()).isEqualTo(expected.getMemberId()),
            ()->assertThat(actual.getProductId()).isEqualTo(expected.getProductId())

            );

    }

    @Test
    void getWishByMemberId(){
        //given
        Wish expected = new Wish(1L,2L);
        wishRepository.save(expected);

        //when
        List<Wish> actual = wishRepository.findByMemberId(1L);

        //then
        assertAll(
            ()->assertThat(actual.get(0).getId()).isNotNull(),
            ()->assertThat(actual.get(0).getMemberId()).isEqualTo(1L),
            ()->assertThat(actual.get(0).getProductId()).isEqualTo(2L)

            );


    }

    @Test
    void deleteWishByMemberIdAndProductId(){
        //given
        Wish expected = new Wish(1L,2L);
        wishRepository.save(expected);

        //when
        wishRepository.deleteByMemberIdAndProductId(1L,2L);
        List<Wish> actual = wishRepository.findByMemberId(1L);

        //then
        assertThat(actual).isNull();
    }

}