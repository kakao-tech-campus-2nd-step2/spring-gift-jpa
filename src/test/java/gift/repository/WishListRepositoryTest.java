package gift.repository;

import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import gift.entity.WishList;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class WishListRepositoryTest {
    
    @Autowired
    private WishListRepository wishListRepository;

    private WishList expected;
    private WishList actual;
    private long notExistMemberId;

    @BeforeEach
    void setUp(){
        expected = new WishList(1L, 1L);
        actual = wishListRepository.save(expected);
        notExistMemberId = 2L;
    }

    @Test
    void save(){
        
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getMemberId()).isEqualTo(expected.getMemberId())
        );
    }

    @Test
    void findWishListById(){

        List<WishList> expectedList = new ArrayList<>();
        expectedList.add(new WishList(1L, 1L));
        expectedList.add(new WishList(1L, 2L));

        wishListRepository.save(new WishList(1L, 2L));
        List<WishList> actual = wishListRepository.findByMemberId(1L);

        assertThat(actual.size()).isEqualTo(expectedList.size());
        assertThat(wishListRepository.findByMemberId(notExistMemberId)).isEmpty();

        for (int i = 0; i < expectedList.size(); i++) {
            assertThat(actual.get(i).getProductId()).isEqualTo(expectedList.get(i).getProductId());
        }
    }

    @Test
    void delete(){

        wishListRepository.delete(expected);
        
        Optional<WishList> wishList = wishListRepository.findById(expected.getId());
        assertThat(wishList).isNotPresent();
    }



}
