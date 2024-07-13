package gift.wishlist;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.member.Member;
import gift.member.MemberRepository;
import gift.product.Product;
import gift.product.ProductRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class WishlistRepositoryTest {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setProductRepository() {
        productRepository.save(new Product("사과", 2000, "www"));
        productRepository.save(new Product("바나나", 3000,"www.com"));
    }

    @BeforeEach
    void setMemberRepository() {
        memberRepository.save(new Member("westzeroright","errorai"));
        memberRepository.save(new Member("econo","404"));
    }

    @Test
    void add() {
        Wishlist expected = new Wishlist(
            memberRepository.findById(1L).get(),
            productRepository.findById(1L).get()
        );
        Wishlist actual = wishlistRepository.save(expected);
        assertAll(
            () -> assertThat(actual).isEqualTo(expected)
        );
    }

    @Test
    void delete() {
        Wishlist expected = new Wishlist(
            memberRepository.findById(1L).get(),
            productRepository.findById(1L).get()
        );
        wishlistRepository.delete(expected);

        List<Wishlist> isWishlist = wishlistRepository.findById(expected.getProduct().getId()).stream().toList();

        assertAll(
            () -> assertThat(isWishlist.size()).isEqualTo(0)
        );

    }

    @Test
    void find() {
        Wishlist expected = new Wishlist(
            memberRepository.findById(1L).get(),
            productRepository.findById(1L).get()
        );
        wishlistRepository.save(expected);

        Wishlist actual = wishlistRepository.findById(expected.getProduct().getId()).get();

        assertAll(
            () -> assertThat(actual.getProduct()).isNotNull(),
            () -> assertThat(actual.getMember()).isNotNull(),
            () -> assertThat(actual.getId()).isEqualTo(expected.getId()),
            () -> assertThat(actual.getProduct().getId()).isEqualTo(1L),
            () -> assertThat(actual.getMember().getId()).isEqualTo(1L)
        );

    }

    @Test
    void findAll() {
        Wishlist expected1 = new Wishlist(
            memberRepository.findById(1L).get(),
            productRepository.findById(1L).get()
        );
        wishlistRepository.save(expected1);

        Wishlist expected2 = new Wishlist(
            memberRepository.findById(2L).get(),
            productRepository.findById(2L).get()
        );
        wishlistRepository.save(expected2);

        List<Wishlist> wishlist = wishlistRepository.findAll();

        assertAll(
            () -> assertThat(wishlist.size()).isEqualTo(2)
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
