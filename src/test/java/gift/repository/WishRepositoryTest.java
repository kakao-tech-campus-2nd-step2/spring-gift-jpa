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
import org.junit.jupiter.api.BeforeEach;
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

  @BeforeEach
  public void setUp() {
    wishRepository.deleteAll();
    userRepository.deleteAll();
    productRepository.deleteAll();
  }

  private User createAndSaveUser(String email, String password) {
    User user = new User();
    user.setEmail(email);
    user.setPassword(password);
    return userRepository.save(user);
  }

  private Product createAndSaveProduct(String name, int price, String imageUrl) {
    Product product = new Product();
    product.setName(name);
    product.setPrice(price);
    product.setImageUrl(imageUrl);
    return productRepository.save(product);
  }

  private Wish createAndSaveWish(User user, Product product) {
    Wish wish = new Wish();
    wish.setUser(user);
    wish.setProduct(product);
    return wishRepository.save(wish);
  }

  @Test
  public void testSaveAndFindById() {
    // given
    User user = createAndSaveUser("test@example.com", "password");
    Product product = createAndSaveProduct("Test Product", 100, "http://example.com/image.jpg");
    Wish wish = createAndSaveWish(user, product);

    // when
    Optional<Wish> foundWish = wishRepository.findById(wish.getId());

    // then
    assertThat(foundWish).isPresent();
    assertThat(foundWish.get().getUser().getEmail()).isEqualTo("test@example.com");
    assertThat(foundWish.get().getProduct().getId()).isEqualTo(product.getId());
  }

  @Test
  public void testFindByUserId() {
    // given
    User user = createAndSaveUser("test@example.com", "password");
    Product product1 = createAndSaveProduct("Test Product 1", 100, "http://example.com/image1.jpg");
    Product product2 = createAndSaveProduct("Test Product 2", 200, "http://example.com/image2.jpg");
    createAndSaveWish(user, product1);
    createAndSaveWish(user, product2);

    // when
    List<Wish> wishes = wishRepository.findByUserId(user.getId());

    // then
    assertThat(wishes).isNotEmpty();
    assertThat(wishes.size()).isEqualTo(2);
  }

  @Test
  public void testFindByProductId() {
    // given
    User user = createAndSaveUser("test2@example.com", "password");
    Product product = createAndSaveProduct("Test Product", 100, "http://example.com/image.jpg");
    createAndSaveWish(user, product);

    // when
    List<Wish> wishes = wishRepository.findByProductId(product.getId());

    // then
    assertThat(wishes).isNotEmpty();
    assertThat(wishes.size()).isEqualTo(1);
  }

  @Test
  public void testDeleteWish() {
    // given
    User user = createAndSaveUser("delete@example.com", "password");
    Product product = createAndSaveProduct("Test Product", 100, "http://example.com/image.jpg");
    Wish wish = createAndSaveWish(user, product);
    Long wishId = wish.getId();

    // when
    wishRepository.deleteById(wishId);
    Optional<Wish> deletedWish = wishRepository.findById(wishId);

    // then
    assertThat(deletedWish).isNotPresent();
  }

  @Test
  public void testFindByUserIdWithPagination() {
    // given
    User user = createAndSaveUser("pagination@example.com", "password");
    for (int i = 1; i <= 15; i++) {
      Product product = createAndSaveProduct("Test Product " + i, 100 * i, "http://example.com/image" + i + ".jpg");
      createAndSaveWish(user, product);
    }

    // when
    Pageable pageable = PageRequest.of(0, 10);
    Page<Wish> wishesPage = wishRepository.findByUserId(user.getId(), pageable);

    // then
    assertThat(wishesPage.getContent()).isNotEmpty();
    assertThat(wishesPage.getContent().size()).isEqualTo(10);
    assertThat(wishesPage.getTotalElements()).isEqualTo(15);
    assertThat(wishesPage.getTotalPages()).isEqualTo(2);
  }
}
