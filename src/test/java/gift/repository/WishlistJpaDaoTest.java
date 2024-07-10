package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.dto.Member;
import gift.dto.Product;
import gift.dto.Wishlist;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class WishlistJpaDaoTest {

    @Autowired
    MemberJpaDao memberJpaDao;
    @Autowired
    ProductJpaDao productJpaDao;
    @Autowired
    WishlistJpaDao wishlistJpaDao;

    @Test
    @DisplayName("위시리스트에 상품 추가 테스트")
    void save() {
        Wishlist wish = wishlistJpaDao.save(new Wishlist("sgoh", 1L));
        assertThat(wish).isNotNull();
    }

    @Test
    @DisplayName("이메일과 상품ID로 조회 테스트")
    void findByEmailAndProductId() {
        wishlistJpaDao.save(new Wishlist("sgoh", 1L));

        Assertions.assertDoesNotThrow(() -> {
            wishlistJpaDao.findByEmailAndProductId("sgoh", 1L).get();
        });
    }

    @Test
    @DisplayName("이메일로 위시리스트 목록 조회 테스트")
    void findWishlistByEmail() {
        memberJpaDao.save(new Member("sgoh", "hello"));
        productJpaDao.save(new Product(1L, "아메리카노", 4500, "https://"));
        productJpaDao.save(new Product(2L, "티", 9000, "https://"));
        wishlistJpaDao.save(new Wishlist("sgoh", 1L));
        wishlistJpaDao.save(new Wishlist("sgoh", 2L));

        List<Product> wishlist = wishlistJpaDao.findAllWishlistByEmail("sgoh");
        assertThat(wishlist.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("이메일과 상품ID로 위시리스트 상품 삭제 테스트")
    void deleteByEmailAndProductId() {
        wishlistJpaDao.save(new Wishlist("sgoh", 1L));
        wishlistJpaDao.save(new Wishlist("sgoh", 2L));

        wishlistJpaDao.deleteByEmailAndProductId("sgoh", 1L);
        assertThat(wishlistJpaDao.findAll().size()).isEqualTo(1);
    }
}