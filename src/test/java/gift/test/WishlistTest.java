package gift.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import gift.controller.WishlistController;
import gift.model.Product;
import gift.model.User;
import gift.model.Wishlist;
import gift.repository.ProductRepository;
import gift.repository.UserRepository;
import gift.repository.WishlistRepository;
import gift.service.AuthService;
import gift.service.WishlistService;

public class WishlistTest {

    @Mock
    private WishlistService wishlistService;

    @InjectMocks
    private WishlistController wishlistController;

    @Mock
    private BindingResult bindingResult;

    private User user;
    private Product product;
    private Wishlist wishlist;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setEmail("test@test.com");
        user.setPassword("pw");

        product = new Product();
        product.setName("아이스 아메리카노 T");
        product.setPrice(4500);
        product.setImageUrl("https://example.com/image.jpg");

        wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setProduct(product);
        wishlist.setQuantity(2);

        when(wishlistService.getWishlist(any(String.class), any(BindingResult.class)))
                .thenReturn(List.of(wishlist));
        doNothing().when(wishlistService).addWishlist(any(String.class), any(Wishlist.class), any(BindingResult.class));
        doNothing().when(wishlistService).removeWishlist(any(String.class), any(Wishlist.class), any(BindingResult.class));
        doNothing().when(wishlistService).updateWishlistQuantity(any(String.class), any(Wishlist.class), any(BindingResult.class));
    }

    @Test
    public void testGetWishlist() {
        ResponseEntity<List<Wishlist>> response = wishlistController.getWishlist("Bearer token", bindingResult);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0).getProduct().getName()).isEqualTo(product.getName());
    }

    @Test
    public void testAddWishlist() {
        ResponseEntity<String> response = wishlistController.addWishlist("Bearer token", wishlist, bindingResult);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Product added to wishlist successfully.");
    }

    @Test
    public void testRemoveWishlist() {
        ResponseEntity<String> response = wishlistController.removeWishlist("Bearer token", wishlist, bindingResult);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Product removed from wishlist successfully.");
    }

    @Test
    public void testUpdateWishlist() {
        wishlist.setQuantity(10);
        ResponseEntity<String> response = wishlistController.updateWishlist("Bearer token", wishlist, bindingResult);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Product quantity updated successfully.");
    }
}