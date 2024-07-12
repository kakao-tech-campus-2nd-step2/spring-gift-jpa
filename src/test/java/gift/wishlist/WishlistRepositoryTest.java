package gift.wishlist;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WishlistRepositoryTest {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Test
    void save() {
        Wishlist expected = new Wishlist(1L, 1L);
        Wishlist actual = wishlistRepository.save(expected);
        assertAll(
            () -> assertThat(actual).isEqualTo(expected)
        );
    }

    @Test
    void find() {
        Wishlist expected = new Wishlist(1L, 1L);
        wishlistRepository.save(expected);
        Wishlist actual = wishlistRepository.findById(expected.getId()).get();

        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getId()).isEqualTo(expected.getId()),
            () -> assertThat(actual.getMemberId()).isEqualTo(1L),
            () -> assertThat(actual.getProductId()).isEqualTo(1L)
        );
    }

    @Test
    void findAll() {
        Wishlist wishlist1 = new Wishlist(1L,1L);
        Wishlist wishlist2 = new Wishlist(2L, 2L);
        Wishlist wishlist3 = new Wishlist(3L, 3L);
        wishlistRepository.save(wishlist1);
        wishlistRepository.save(wishlist2);
        wishlistRepository.save(wishlist3);

        List<Wishlist> wishlist = wishlistRepository.findAll();

        assertAll(
            () -> assertThat(wishlist.size()).isEqualTo(3)
        );
    }

    @Test
    void delete() {
        Wishlist wishlist = new Wishlist(1L, 1L);
        wishlistRepository.save(wishlist);
        wishlistRepository.deleteById(wishlist.getId());

        List<Wishlist> isWishlist = wishlistRepository.findById(wishlist.getId()).stream().toList();

        assertAll(
            () -> assertThat(isWishlist.size()).isEqualTo(0)
        );
    }
}
//package gift.wishlist;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertAll;
//
//import java.util.List;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class WishlistRepositoryTest {
//
//    @Autowired
//    private WishlistRepository wishlistRepository;
//
//    @Test
//    void save() {
//        Wishlist expected = new Wishlist(1L, 1L);
//        Wishlist actual = wishlistRepository.save(expected);
//        assertAll(
//            () -> assertThat(actual).isEqualTo(expected)
//        );
//    }
//
//    @Test
//    void find() {
//        Wishlist expected = new Wishlist(1L, 1L);
//        wishlistRepository.save(expected);
//        Wishlist actual = wishlistRepository.findById(expected.getId()).get();
//
//        assertAll(
//            () -> assertThat(actual.getId()).isNotNull(),
//            () -> assertThat(actual.getId()).isEqualTo(expected.getId()),
//            () -> assertThat(actual.getMemberId()).isEqualTo(1L),
//            () -> assertThat(actual.getProductId()).isEqualTo(1L)
//        );
//    }
//
//    @Test
//    void findAll() {
//        Wishlist wishlist1 = new Wishlist(1L,1L);
//        Wishlist wishlist2 = new Wishlist(2L, 2L);
//        Wishlist wishlist3 = new Wishlist(3L, 3L);
//        wishlistRepository.save(wishlist1);
//        wishlistRepository.save(wishlist2);
//        wishlistRepository.save(wishlist3);
//
//        List<Wishlist> wishlist = wishlistRepository.findAll();
//
//        assertAll(
//            () -> assertThat(wishlist.size()).isEqualTo(3)
//        );
//    }
//
//    @Test
//    void delete() {
//        Wishlist wishlist = new Wishlist(1L, 1L);
//        wishlistRepository.save(wishlist);
//        wishlistRepository.deleteById(wishlist.getId());
//
//        List<Wishlist> isWishlist = wishlistRepository.findById(wishlist.getId()).stream().toList();
//
//        assertAll(
//            () -> assertThat(isWishlist.size()).isEqualTo(0)
//        );
//    }
//}
