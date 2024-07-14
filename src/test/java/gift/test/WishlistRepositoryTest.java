package gift.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import gift.model.Product;
import gift.model.User;
import gift.model.Wishlist;
import gift.repository.ProductRepository;
import gift.repository.UserRepository;
import gift.repository.WishlistRepository;

@DataJpaTest
public class WishlistRepositoryTest {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;
    
    private User user;
    private Product product;

    @BeforeEach
    void setUp() {
    	 user = new User("tset@test.com", "pw");
    	 userRepository.save(user);
    	 product = new Product("아이스 아메리카노 T", 4500, "https://example.com/image.jpg");
    	 productRepository.save(product);
    }
    
    @Test
    void save() {
    	Wishlist expected = new Wishlist(user, product);
    	expected.setQuantity(2);
    	Wishlist actual = wishlistRepository.save(expected);

    	assertThat(actual.getId()).isNotNull();
    	assertThat(actual.getUser()).isEqualTo(expected.getUser());
    	assertThat(actual.getProduct()).isEqualTo(expected.getProduct());
    	assertThat(actual.getQuantity()).isEqualTo(expected.getQuantity());
    }

    @Test
    void findByUserId() {
    	for(int i=1; i<=15; i++) {
    		Product product = new Product("product"+1, 4500, "https://example.com/image.jpg");
    		productRepository.save(product);
    		Wishlist wishlist = new Wishlist(user, product);
    		wishlist.setQuantity(i);
    		wishlistRepository.save(wishlist);
    	}

    	Pageable pageable = PageRequest.of(0, 10);
    	Page<Wishlist> wishlistItemsPage = wishlistRepository.findByUserId(user.getId(), pageable);
    	
    	assertThat(wishlistItemsPage.getContent()).hasSize(10);
    	assertThat(wishlistItemsPage.getTotalElements()).isEqualTo(15);
    	assertThat(wishlistItemsPage.getNumber()).isEqualTo(0);
    	assertThat(wishlistItemsPage.getTotalElements()).isEqualTo(2);
    }
}
