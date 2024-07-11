package gift;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.Model.Wishlist;
import gift.Repository.WishlistRepository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
public class WishlistRepositoryTest {
    @Autowired
    private WishlistRepository wishlistRepository;

    @Test
    void findAll(){
        Wishlist expected1 = new Wishlist("A",1000,"A");
        Wishlist expected2 = new Wishlist("B",2000,"B");

        wishlistRepository.save(expected1);
        wishlistRepository.save(expected2);
        List<Wishlist> wishlists = wishlistRepository.findAll();

        Wishlist actual1 = wishlists.get(0);
        Wishlist actual2 = wishlists.get(1);

        assertAll(
            () -> assertThat(actual1.getId()).isNotNull(),
            () -> assertThat(actual1.getName()).isEqualTo(expected1.getName()),
            () -> assertThat(actual1.getPrice()).isEqualTo(expected1.getPrice()),
            () -> assertThat(actual1.getImageUrl()).isEqualTo(expected1.getImageUrl()),

            () -> assertThat(actual2.getId()).isNotNull(),
            () -> assertThat(actual2.getName()).isEqualTo(expected2.getName()),
            () -> assertThat(actual2.getPrice()).isEqualTo(expected2.getPrice()),
            () -> assertThat(actual2.getImageUrl()).isEqualTo(expected2.getImageUrl())
        );

    }

    @Test
    void save(){
        Wishlist expected = new Wishlist("AAA",1000,"A");
        Wishlist actual = wishlistRepository.save(expected);
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
            () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl())
        );
    }

    @Test
    void deleteById(){
        Wishlist example = new Wishlist("AAAA",1000,"A");
        Wishlist wishlist = wishlistRepository.save(example);
        wishlistRepository.deleteById(wishlist.getId());
        List<Wishlist> wishlists = wishlistRepository.findAll();
        assertThat(wishlists.isEmpty()).isEqualTo(true);
    }
}
