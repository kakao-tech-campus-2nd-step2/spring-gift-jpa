package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.product.entity.Product;
import gift.product.repository.ProductRepository;
import gift.user.entity.User;
import gift.user.repository.UserRepository;
import gift.wish.entity.Wish;
import gift.wish.repository.WishRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
public class WishRepositoryTest {

  @Autowired
  private WishRepository wishRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ProductRepository productRepository;

  @Test
  public void testSaveAndFindById() {
    User user = new User();
    user.setEmail("test@example.com");
    user.setPassword("password");
    userRepository.save(user);

    Product product = new Product();
    product.setName("Test Product");
    product.setPrice(100);
    product.setImageUrl("http://example.com/image.jpg");
    productRepository.save(product);

    Wish wish = new Wish();
    wish.setUser(user);
    wish.setProduct(product);
    wishRepository.save(wish);

    Optional<Wish> foundWish = wishRepository.findById(wish.getId());

    assertThat(foundWish).isPresent();
    assertThat(foundWish.get().getUser().getEmail()).isEqualTo("test@example.com");
    assertThat(foundWish.get().getProduct().getId()).isEqualTo(product.getId());
  }

  @Test
  public void testFindByUser() {
    User user = new User();
    user.setEmail("test@example.com");
    user.setPassword("password");
    userRepository.save(user);

    Product product1 = new Product();
    product1.setName("Test Product 1");
    product1.setPrice(100);
    product1.setImageUrl("http://example.com/image1.jpg");
    productRepository.save(product1);

    Product product2 = new Product();
    product2.setName("Test Product 2");
    product2.setPrice(200);
    product2.setImageUrl("http://example.com/image2.jpg");
    productRepository.save(product2);

    Wish wish1 = new Wish();
    wish1.setUser(user);
    wish1.setProduct(product1);
    wishRepository.save(wish1);

    Wish wish2 = new Wish();
    wish2.setUser(user);
    wish2.setProduct(product2);
    wishRepository.save(wish2);

    List<Wish> wishes = wishRepository.findByUser(user);

    assertThat(wishes).isNotEmpty();
    assertThat(wishes.size()).isEqualTo(2);
  }

  @Test
  public void testFindByProductId() {
    User user = new User();
    user.setEmail("test2@example.com");
    user.setPassword("password");
    userRepository.save(user);

    Product product = new Product();
    product.setName("Test Product");
    product.setPrice(100);
    product.setImageUrl("http://example.com/image.jpg");
    productRepository.save(product);

    Wish wish = new Wish();
    wish.setUser(user);
    wish.setProduct(product);
    wishRepository.save(wish);

    List<Wish> wishes = wishRepository.findByProductId(product.getId());

    assertThat(wishes).isNotEmpty();
    assertThat(wishes.size()).isEqualTo(1);
  }

  @Test
  public void testDeleteWish() {
    User user = new User();
    user.setEmail("delete@example.com");
    user.setPassword("password");
    userRepository.save(user);

    Product product = new Product();
    product.setName("Test Product");
    product.setPrice(100);
    product.setImageUrl("http://example.com/image.jpg");
    productRepository.save(product);

    Wish wish = new Wish();
    wish.setUser(user);
    wish.setProduct(product);
    wishRepository.save(wish);

    Long wishId = wish.getId();
    wishRepository.deleteById(wishId);

    Optional<Wish> deletedWish = wishRepository.findById(wishId);

    assertThat(deletedWish).isNotPresent();
  }

  @Test
  public void testFindByUserWithPagination() {
    User user = new User();
    user.setEmail("pagination@example.com");
    user.setPassword("password");
    userRepository.save(user);

    for (int i = 1; i <= 15; i++) {
      Product product = new Product();
      product.setName("Test Product " + i);
      product.setPrice(100 * i);
      product.setImageUrl("http://example.com/image" + i + ".jpg");
      productRepository.save(product);

      Wish wish = new Wish();
      wish.setUser(user);
      wish.setProduct(product);
      wishRepository.save(wish);
    }

    Pageable pageable = PageRequest.of(0, 10);
    Page<Wish> wishesPage = wishRepository.findByUser(user, pageable);

    assertThat(wishesPage.getContent()).isNotEmpty();
    assertThat(wishesPage.getContent().size()).isEqualTo(10);
    assertThat(wishesPage.getTotalElements()).isEqualTo(15);
    assertThat(wishesPage.getTotalPages()).isEqualTo(2);
  }
}
