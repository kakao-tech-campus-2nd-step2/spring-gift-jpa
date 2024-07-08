package gift.repository;

import static org.junit.jupiter.api.Assertions.assertAll;

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

    @Test
    void save(){
        
        WishList expected = new WishList(1L, 1L);
        WishList actual = wishListRepository.save(expected);
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getMemberId()).isEqualTo(expected.getMemberId())
        );
    }

    @Test
    void findWishListById(){

        List<WishList> expected = new ArrayList<>();
        expected.add(new WishList(1L, 1L));
        expected.add(new WishList(1L, 2L));

        wishListRepository.save(new WishList(1L, 1L));
        wishListRepository.save(new WishList(1L, 2L));
        List<WishList> actual = wishListRepository.findProductIdsByMemberId(1L);

        assertThat(actual.size()).isEqualTo(expected.size());

        for (int i = 0; i < expected.size(); i++) {
            assertThat(actual.get(i).getProductId()).isEqualTo(expected.get(i).getProductId());
        }
    }

    @Test
    void delete(){

        WishList expected = new WishList(1L, 1L);
        wishListRepository.save(expected);
        
        long id = wishListRepository.findId(1L, 1L);

        wishListRepository.deleteById(id);
        
        Optional<WishList> wishList = wishListRepository.findById(id);
        assertThat(wishList).isNotPresent();
    }



}
