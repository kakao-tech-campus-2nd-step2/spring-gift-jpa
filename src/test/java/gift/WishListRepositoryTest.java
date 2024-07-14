package gift;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.exception.ErrorCode;
import gift.exception.RepositoryException;
import gift.model.Member;
import gift.model.Product;
import gift.model.WishList;
import gift.model.WishListDTO;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
public class WishListRepositoryTest {

    @Autowired
    private WishListRepository wishlists;
    @Autowired
    private ProductRepository products;
    @Autowired
    private MemberRepository members;
    private Product productA;
    private Product productB;
    private Member memberA;
    private Member memberB;

    private WishList createWishList(Member member, Product product, long quantity) {
        return new WishList(member, product, quantity);
    }

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
    @DisplayName("WishList 저장")
    void save() {
        var expected = createWishList(memberA, productA, 1);
        wishlists.save(expected);
        assertThat(wishlists.findAll()).isNotEmpty();
    }

    @Test
    @DisplayName("memberA와 memberB의 WishList 조회")
    void saveWishLists() {
        var wishList1 = createWishList(memberA, productA, 1);
        wishlists.save(wishList1);
        var wishList2 = createWishList(memberB, productA, 2);
        wishlists.save(wishList2);
        var wishList3 = createWishList(memberB, productB, 1);
        wishlists.save(wishList3);

        Pageable pageable = PageRequest.of(0, 2);

        assertAll(
            () -> assertThat(
                wishlists.findWishListByMemberId(memberA.getId(), pageable)
                    .stream()
                    .toList()
                    .size())
                .isEqualTo(1),
            () -> assertThat(
                wishlists.findWishListByMemberId(memberB.getId(), pageable)
                    .stream()
                    .toList()
                    .size())
                .isEqualTo(2)
        );
    }

    @DisplayName("WishList 업데이트")
    @Test
    void update() {
        var wishlist = new WishList(memberA, productA, 1);
        wishlists.save(wishlist);

        var update = wishlists.findByMemberIdAndProductId(memberA.getId(), productA.getId())
            .orElseThrow(
                () -> new RepositoryException(ErrorCode.WISHLIST_NOT_FOUND, memberA.getId(),
                    productA.getId()));

        update.setQuantity(3);

        assertThat(update.getQuantity()).isEqualTo(3);
    }

    private WishListDTO converToDTO(WishList wishList) {
        return new WishListDTO(wishList.getMember().getId(), wishList.getProduct().getId(),
            wishList.getQuantity());
    }
}
