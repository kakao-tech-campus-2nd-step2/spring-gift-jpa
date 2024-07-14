package gift.repository;

import gift.entity.Product;
import gift.entity.ProductName;
import gift.entity.User;
import gift.entity.Wish;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class WishRepositoryTest {

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void 데이터_정리() {
        wishRepository.deleteAll();
        productRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void 위시리스트_저장_후_조회_성공() {
        Product product = new Product(new ProductName("오둥이 입니다만"), 29800, "https://example.com/product1.jpg");
        productRepository.save(product);

        User user = new User("test@example.com", "password");
        userRepository.save(user);

        Wish wish = new Wish(user, product);
        wishRepository.save(wish);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Wish> wishesPage = wishRepository.findByUser(user, pageable);
        List<Wish> wishes = wishesPage.getContent();

        assertEquals(1, wishes.size());
        assertEquals(product.getId(), wishes.get(0).getProduct().getId());
    }

    @Test
    public void 위시리스트_삭제_성공() {
        Product product = new Product(new ProductName("오둥이 입니다만"), 29800, "https://example.com/product1.jpg");
        productRepository.save(product);

        User user = new User("test@example.com", "password");
        userRepository.save(user);

        Wish wish = new Wish(user, product);
        wishRepository.save(wish);

        wishRepository.delete(wish);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Wish> wishesPage = wishRepository.findByUser(user, pageable);
        List<Wish> wishes = wishesPage.getContent();

        assertTrue(wishes.isEmpty());
    }
}
