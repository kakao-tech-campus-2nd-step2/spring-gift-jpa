//package gift.wishlist.service;
//
//import gift.product.model.Product;
//import gift.member.model.Member;
//import gift.wishlist.model.WishList;
//import gift.wishlist.repository.WishListRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ActiveProfiles("test")
//public class WishListServiceTest {
//
//    @Autowired
//    private WishListRepository wishListRepository;
//
//    @Test
//    @Transactional
//    public void addProductToWishListTest() {
//        // Given
//        Member member = new Member("test@example.com", "password");
//        WishList wishList = new WishList(member, new Product("Product 1", 10, "http://www.google.com"));
//        wishListRepository.save(wishList);
//
//        Product newProduct = new Product("Product 2", 20, "http://www.google.com");
//
//        // When
//        WishListService.addProductToWishList(member.member_id(), newProduct);
//
//        // Then
//        WishList updatedWishList = wishListRepository.findByUserId(member.member_id());
//        assertThat(updatedWishList.products()).hasSize(2);
//        assertThat(updatedWishList.products()).extracting(Product::name).contains("Product 2");
//    }
//
//    @Test
//    @Transactional
//    public void removeProductFromWishListTest() {
//        // Given
//        Member member = new Member("test@example.com", "password");
//        Product product1 = new Product("Product 1", 10, "http://www.google.com");
//        Product product2 = new Product("Product 2", 20, "http://www.google.com");
//        WishList wishList = new WishList(member, List.of(product1, product2));
//        wishListRepository.save(wishList);
//
//        // When
//        WishListService.removeProductFromWishList(member.member_id(), product2.product_id());
//
//        // Then
//        WishList updatedWishList = wishListRepository.findByUserId(member.member_id());
//        assertThat(updatedWishList.products()).hasSize(1);
//        assertThat(updatedWishList.products()).extracting(Product::name).doesNotContain("Product 2");
//    }
//}