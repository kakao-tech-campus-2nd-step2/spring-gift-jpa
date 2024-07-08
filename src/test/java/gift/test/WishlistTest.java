package gift.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import gift.controller.WishlistController;
import gift.model.Wishlist;
import gift.service.WishlistService;

public class WishlistTest {

	@Mock
    private WishlistService wishlistService;

    @InjectMocks
    private WishlistController wishlistController;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetWishlist() {
    	Wishlist wishlistItem = new Wishlist();
        wishlistItem.setId(1L);
        wishlistItem.setProductName("testProductName");
        wishlistItem.setQuantity(1);
        
        List<Wishlist> wishlist = new ArrayList<>();
        wishlist.add(wishlistItem);
        
        when(wishlistService.getWishlist(anyString(), any(BindingResult.class))).thenReturn(wishlist);

        ResponseEntity<List<Wishlist>> response = wishlistController.getWishlist("Bearer token", bindingResult);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(wishlistItem, response.getBody().get(0));
    }

    @Test
    public void testAddWishlist() {
        doNothing().when(wishlistService).addWishlist(anyString(), any(Wishlist.class), any(BindingResult.class));

        Wishlist wishlist = new Wishlist();
        wishlist.setProductName("testProductName");
        wishlist.setQuantity(1);

        ResponseEntity<String> response = wishlistController.addWishlist("Bearer token", wishlist, bindingResult);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Product added to wishlist successfully.", response.getBody());
    }

    @Test
    public void testRemoveWishlist() {
        doNothing().when(wishlistService).removeWishlist(anyString(), any(Wishlist.class), any(BindingResult.class));

        Wishlist wishlist = new Wishlist();
        wishlist.setProductName("testProductName");

        ResponseEntity<String> response = wishlistController.removeWishlist("Bearer token", wishlist, bindingResult);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Product removed from wishlist successfully.", response.getBody());
    }

    @Test
    public void testUpdateWishlist() {
        doNothing().when(wishlistService).updateWishlistQuantity(anyString(), any(Wishlist.class), any(BindingResult.class));

        Wishlist wishlist = new Wishlist();
        wishlist.setProductName("testProductName");
        wishlist.setQuantity(10);

        ResponseEntity<String> response = wishlistController.updateWishlist("Bearer token", wishlist, bindingResult);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Product quantity updated successfully.", response.getBody());
    }
}
