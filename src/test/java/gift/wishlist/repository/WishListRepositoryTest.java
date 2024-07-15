package gift.wishlist.repository;

import gift.member.model.Member;
import gift.product.model.Product;
import gift.wishlist.model.WishList;
import gift.wishlist.repository.WishListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class WishListRepositoryTest {

    @Autowired
    private WishListRepository wishListRepository;

    private Member member;
    private Product product1;
    private Product product2;

    @BeforeEach
    public void setUp() {
        member = new Member("test@example.com", "password");
        product1 = new Product("Product 1", 10, "http://www.google.com");
        product2 = new Product("Product 2", 20, "http://www.google.com");
    }

    @Test
    public void testSaveWishList() {
        // Given
        WishList wishList = new WishList(member, product1);

        // When
        WishList savedWishList = wishListRepository.save(wishList);

        // Then
        assertThat(savedWishList).isNotNull();
        assertThat(savedWishList.wishlist_id()).isNotNull();
        assertThat(savedWishList.products()).contains(product1);
    }

    @Test
    public void testFindByMemberId() {
        // Given
        WishList wishList = new WishList(member, product1);
        wishListRepository.save(wishList);

        // When
        WishList foundWishList = wishListRepository.findByMemberId(member.getMemberId());

        // Then
        assertThat(foundWishList).isNotNull();
        assertThat(foundWishList.member().getEmail()).isEqualTo("test@example.com");
        assertThat(foundWishList.products()).contains(product1);
    }

    @Test
    public void testRemoveProductFromWishList() {
        // Given
        WishList wishList = new WishList(member, List.of(product1, product2));
        wishListRepository.save(wishList);

        // When
        wishList.products().remove(product2);
        wishListRepository.save(wishList);

        // Then
        WishList updatedWishList = wishListRepository.findByMemberId(member.getMemberId());
        assertThat(updatedWishList.products()).hasSize(1);
        assertThat(updatedWishList.products()).extracting(Product::name).doesNotContain("Product 2");
    }
}