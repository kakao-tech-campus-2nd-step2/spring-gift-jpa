package gift;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.exception.ErrorCode;
import gift.exception.RepositoryException;
import gift.model.Member;
import gift.model.Product;
import gift.model.WishList;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class WishListRepositoryTest {

    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private ProductRepository products;
    @Autowired
    private MemberRepository members;
    private Product productA;
    private Product productB;
    private Member memberA;
    private Member memberB;

    @BeforeEach
    void setUp() {
        // Product 객체 저장
        productA = new Product(1L, "Product A", 4500, "image-Product_A.com");
        productB = new Product(2L, "Product B", 5500, "image-Product_B.com");
        products.save(productA);
        products.save(productB);

        // Member 객체 저장
        memberA = new Member("member1@test.com", "password");
        memberB = new Member("member2@test.com", "password");
        members.save(memberA);
        members.save(memberB);
    }

    @Test
    @DisplayName("WishListA 저장 / 삭제")
    void saveWishListA() {
        WishList expected = new WishList(memberA.getEmail(), memberA, productA, 1);
        WishList actual = wishListRepository.save(expected);
        assertThat(actual).isEqualTo(expected);

        wishListRepository.deleteById(actual.getId());
        assertThat(wishListRepository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("memberA와 memberB의 WishList 저장 / 조회")
    void saveWishLists() {
        WishList wishList1 = new WishList(memberA.getEmail(), memberA, productA, 1);
        wishListRepository.save(wishList1);
        WishList wishList2 = new WishList(memberB.getEmail(), memberB, productA, 1);
        wishListRepository.save(wishList2);
        WishList wishList3 = new WishList(memberB.getEmail(), memberB, productB, 2);
        wishListRepository.save(wishList3);
        assertAll(
            () -> assertThat(
                wishListRepository.findWishListByMemberId(memberA.getId()).size()).isEqualTo(1),
            () -> assertThat(
                wishListRepository.findWishListByMemberId(memberB.getId()).size()).isEqualTo(2)
        );
    }

    @Test
    void findByMemberIdAndProductName() {
        WishList wishList1 = new WishList(memberA.getEmail(), memberA, productA, 1);
        WishList wishList2 = new WishList(memberA.getEmail(), memberA, productB, 1);
        wishListRepository.save(wishList1);
        wishListRepository.save(wishList2);

        assertThat(wishListRepository.findByMemberIdAndProductName(memberA.getId(),
            productA.getName()).orElseThrow(
            () -> new RepositoryException(ErrorCode.PRODUCT_NOT_FOUND, productA.getName()))
        ).isNotNull();
    }
}
