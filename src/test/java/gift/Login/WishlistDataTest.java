package gift.Login;

import gift.Login.model.Product;
import gift.Login.model.Wishlist;
import gift.Login.repository.ProductRepository;
import gift.Login.repository.WishlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class WishlistDataTest {

    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private ProductRepository productRepository;

    private Product product;
    private Wishlist wishlist;

    @BeforeEach
    public void setUp() {
        // given
        // product 저장
        product = new Product();
        product.setName("Example Product");
        product.setPrice(1999);
        product.setTemperatureOption("Hot");
        product.setCupOption("Reusable");
        product.setSizeOption("Large");
        product.setImageUrl("http://example.com/product.jpg");
        productRepository.save(product);

        // wishlist 저장
        wishlist = new Wishlist();
        wishlist.setMemberId(1L);
        wishlist.getProducts().add(product);
        product.setWishlist(wishlist);
        wishlistRepository.save(wishlist);
    }

    @Test
    public void testCreateWishlistWithProduct() {
        // when
        Wishlist fetchedWishlist = wishlistRepository.findByMemberId(1L).orElse(null);

        // then
        assertThat(fetchedWishlist).isNotNull();
        assertThat(fetchedWishlist.getMemberId()).isEqualTo(1L);
        assertThat(fetchedWishlist.getProducts()).hasSize(1);
        assertThat(fetchedWishlist.getProducts().get(0).getName()).isEqualTo("Example Product");
    }

    @Test
    public void testReadWishlist() {
        // when
        Wishlist fetchedWishlist = wishlistRepository.findByMemberId(1L).orElse(null);

        // then
        assertThat(fetchedWishlist).isNotNull();
        assertThat(fetchedWishlist.getMemberId()).isEqualTo(1L);
        assertThat(fetchedWishlist.getProducts()).hasSize(1);
        assertThat(fetchedWishlist.getProducts().get(0).getName()).isEqualTo("Example Product");
    }

    @Test
    public void testUpdateProductInWishlist() {
        // given
        Wishlist fetchedWishlist = wishlistRepository.findByMemberId(1L).orElse(null);
        assertThat(fetchedWishlist).isNotNull();
        Product fetchedProduct = fetchedWishlist.getProducts().get(0);
        fetchedProduct.setName("Updated Product");
        fetchedProduct.setPrice(2999);

        // when
        productRepository.save(fetchedProduct);
        Wishlist updatedWishlist = wishlistRepository.findByMemberId(1L).orElse(null);

        // then
        assertThat(updatedWishlist).isNotNull();
        assertThat(updatedWishlist.getProducts()).hasSize(1);
        assertThat(updatedWishlist.getProducts().get(0).getName()).isEqualTo("Updated Product");
        assertThat(updatedWishlist.getProducts().get(0).getPrice()).isEqualTo(2999);
    }

    @Test
    public void testDeleteProductFromWishlist() {
        // given
        Wishlist fetchedWishlist = wishlistRepository.findByMemberId(1L).orElse(null);
        assertThat(fetchedWishlist).isNotNull();
        Product fetchedProduct = fetchedWishlist.getProducts().get(0);

        // when
        fetchedWishlist.getProducts().remove(fetchedProduct);
        productRepository.delete(fetchedProduct);
        wishlistRepository.save(fetchedWishlist);
        Wishlist updatedWishlist = wishlistRepository.findByMemberId(1L).orElse(null);

        // then
        assertThat(updatedWishlist).isNotNull();
        assertThat(updatedWishlist.getProducts()).isEmpty();
    }
}
