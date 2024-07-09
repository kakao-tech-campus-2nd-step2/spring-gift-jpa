package gift;

import static org.assertj.core.api.Assertions.assertThat;

import gift.wishlist.WishList;
import gift.wishlist.WishListRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class WishListRepositoryTest {
    @Autowired
    private WishListRepository wishListRepository;

    @Test
    void save(){
        WishList wishList = new WishList("admin@email.com",1L,3);
        WishList actual = wishListRepository.save(wishList);
        assertThat(actual.getEmail()).isEqualTo("admin@email.com");
        assertThat(actual.getProductId()).isEqualTo(1L);
        assertThat(actual.getNum()).isEqualTo(3);
    }

    @Test
    void findByEmail() {
        WishList wishList = new WishList("admin@email.com",1L,3);
        wishListRepository.save(wishList);
        WishList actual = wishListRepository.findByEmail(wishList.getEmail());
        assertThat(actual.getProductId()).isEqualTo(1L);
        assertThat(actual.getNum()).isEqualTo(3);
    }

    @Test
    void deleteByEmailAndProductId() {
        WishList wishList = new WishList("admin@email.com",1L,3);
        wishListRepository.save(wishList);
        wishListRepository.deleteByEmailAndProductId("admin@email.com",1L);
        Optional<WishList> actual = wishListRepository.findById(wishList.getId());
        assertThat(actual).isNotPresent();
    }
}
