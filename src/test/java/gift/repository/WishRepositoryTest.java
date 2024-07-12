package gift.repository;

import gift.model.wish.Wish;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class WishRepositoryTest {

    @Autowired
    private WishRepository wishRepository;

    /*@Test
    void save(){
        Wish newWish = new Wish(1L, 1L, 123);
        Wish savedWish = wishRepository.save(newWish);
        assertAll(
                () -> assertThat(savedWish.getProductId()).isNotNull(),
                () -> assertThat(savedWish.getMemberId()).isEqualTo(newWish.getMemberId())
        );
    }

    @Test
    void delete(){
        Wish newWish = new Wish(1L, 1L, 123);
        wishRepository.save(newWish);
        wishRepository.delete(newWish);
        Optional<Wish> actual = wishRepository.findById(1L);
        assertThat(actual).isEmpty();
    }*/
}