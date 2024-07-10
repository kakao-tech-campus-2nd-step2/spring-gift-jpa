package gift.test;

import gift.model.Wishlist;
import gift.repository.WishlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@ComponentScan(basePackages = {"gift"})
public class WishlistRepositoryTest {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        wishlistRepository.deleteAll(); // Clear existing data
    }

    @Test
    @Order(1)
    @DisplayName("담긴 상품 수량 변경")
    @WithMockUser(username = "testuser")
    void testUpdateWishlistQuantity() throws Exception {
        // Given
        Wishlist item = new Wishlist();
        item.setUsername("testuser");
        item.setProductId(1L);
        item.setQuantity(2);
        wishlistRepository.save(item);

        // When
        mockMvc.perform(post("/web/wishlist/update/" + item.getId())
                .param("quantity", "5"))
            .andExpect(status().isOk());

        // Then
        Wishlist updatedItem = wishlistRepository.findById(item.getId()).orElseThrow();
        assertThat(updatedItem.getQuantity()).isEqualTo(5);
    }

    @Test
    @Order(2)
    @DisplayName("담긴 상품 삭제")
    @WithMockUser(username = "testuser")
    void testRemoveFromWishlist() throws Exception {
        // Given
        Wishlist item = new Wishlist();
        item.setUsername("testuser");
        item.setProductId(1L);
        item.setQuantity(2);
        wishlistRepository.save(item);

        // When
        mockMvc.perform(post("/web/wishlist/delete/" + item.getId()))
            .andExpect(status().isOk());

        // Then
        assertThat(wishlistRepository.findById(item.getId())).isEmpty();
    }
}
