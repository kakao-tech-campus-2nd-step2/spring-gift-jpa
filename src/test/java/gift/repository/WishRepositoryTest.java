package gift.repository;

import gift.entity.Product;
import gift.entity.ProductName;
import gift.entity.Wish;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class WishRepositoryTest {

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void 위시리스트_저장_조회() {
        Product product = new Product(new ProductName("오둥이 입니다만"), 29800, "https://example.com/product1.jpg");
        productRepository.save(product);

        Wish wish = new Wish(1L, product.getId());
        wishRepository.save(wish);

        List<Wish> wishes = wishRepository.findByUserId(1L);
        assertEquals(1, wishes.size());
        assertEquals(product.getId(), wishes.get(0).getProductId());
    }

    @Test
    public void 위시리스트_삭제() {
        Product product = new Product(new ProductName("오둥이 입니다만"), 29800, "https://example.com/product1.jpg");
        productRepository.save(product);

        Wish wish = new Wish(1L, product.getId());
        wishRepository.save(wish);

        wishRepository.delete(wish);

        List<Wish> wishes = wishRepository.findByUserId(1L);
        assertTrue(wishes.isEmpty());
    }
}
