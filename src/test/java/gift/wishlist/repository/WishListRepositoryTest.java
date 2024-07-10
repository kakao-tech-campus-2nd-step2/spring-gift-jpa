package gift.wishlist.repository;

import static org.junit.jupiter.api.Assertions.*;

import gift.wishlist.entity.WishList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class WishListRepositoryTest {

    @Autowired
    private WishListRepository wishListRepository;

    @BeforeEach
    void setUp() {
        wishListRepository.deleteAll();

        // 임의의 위시리스트 3개
        List<WishList> wishLists = List.of(
                new WishList(1L, 1L, 3),
                new WishList(1L, 2L, 2),
                new WishList(1L, 3L, 5)
        );

        wishListRepository.saveAll(wishLists);
    }

    @Test
    @DisplayName("위시리스트 생성")
    void addWishList() {
        //given
        WishList wishList = new WishList(1L, 4L, 1);

        //when
        WishList savedWishList = wishListRepository.save(wishList);

        //then
        assertAll(
                () -> assertNotNull(savedWishList.getId()),
                () -> assertEquals(wishList.getMemberId(), savedWishList.getMemberId()),
                () -> assertEquals(wishList.getProductId(), savedWishList.getProductId()),
                () -> assertEquals(wishList.getQuantity(), savedWishList.getQuantity())
        );
    }

    @Test
    @DisplayName("위시리스트 조회")
    void findWishList() {
        // when
        List<WishList> wishLists = wishListRepository.findAll();

        // then
        assertEquals(3, wishLists.size());

        wishLists.forEach(
                wishList -> assertAll(
                        () -> assertNotNull(wishList.getId()),
                        () -> assertNotNull(wishList.getMemberId()),
                        () -> assertNotNull(wishList.getProductId()),
                        () -> assertNotNull(wishList.getQuantity())
                )
        );

        // 존재하지 않는 위시리스트 조회
        assertFalse(wishListRepository.existsByMemberIdAndProductId(1L, 4L));
    }

    @Test
    @DisplayName("위시리스트 수정")
    void updateWishList() {
        //given
        WishList wishList = wishListRepository.findAll().getLast();
        int quantityToAdded = 3;

        // 기존 수량 저장
        int beforeQuantity = wishList.getQuantity();

        //when
        wishList.addQuantity(quantityToAdded);

        //then
        WishList updatedWishList = wishListRepository.findById(wishList.getId()).get();

        assertAll(
                () -> assertEquals(beforeQuantity + quantityToAdded, updatedWishList.getQuantity()),
                () -> assertEquals(wishList.getMemberId(), updatedWishList.getMemberId()),
                () -> assertEquals(wishList.getProductId(), updatedWishList.getProductId()),
                () -> assertEquals(wishList.getId(), updatedWishList.getId())
        );
    }

    @Test
    @DisplayName("위시리스트 삭제")
    void deleteWishList() {
        //given
        WishList wishList = wishListRepository.findAll().getFirst();

        //when
        wishListRepository.deleteById(wishList.getId());

        //then
        assertFalse(wishListRepository.existsById(wishList.getId()));
    }
}