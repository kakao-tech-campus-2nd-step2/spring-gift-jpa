package gift.repository;
import static org.assertj.core.api.Assertions.assertThat;
import gift.product.entity.Product;
import gift.product.repository.ProductRepository;
import gift.wish.repository.WishRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@DataJpaTest
public class ProductRepositoryTest {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private WishRepository wishRepository;

  @BeforeEach
  public void setUp() {
    wishRepository.deleteAll();
    productRepository.deleteAll();
  }

  private Product createAndSaveProduct(String name, int price, String imageUrl) {
    Product product = new Product();
    product.setName(name);
    product.setPrice(price);
    product.setImageUrl(imageUrl);
    return productRepository.save(product);
  }

  @Test
  public void testSaveAndFindProduct() {
    // given
    Product product = createAndSaveProduct("딸기 아사이", 5900, "https://img1.kakaocdn.net/thumb/C320x320@2x.fwebp.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20231010111407_7fcb10e99eec4365af527f0bb3d27a0e.jpg");

    // when
    Optional<Product> foundProduct = productRepository.findById(product.getId());

    // then
    assertThat(foundProduct).isPresent();

    foundProduct.ifPresent(p -> {
      assertThat(p.getName()).isEqualTo("딸기 아사이");
      assertThat(p.getPrice()).isEqualTo(5900);
      assertThat(p.getImageUrl()).isEqualTo("https://img1.kakaocdn.net/thumb/C320x320@2x.fwebp.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20231010111407_7fcb10e99eec4365af527f0bb3d27a0e.jpg");
    });
  }

  @Test
  public void testUpdateProduct() {
    // given
    Product product = createAndSaveProduct("딸기 아사이", 5900, "https://img1.kakaocdn.net/thumb/C320x320@2x.fwebp.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20231010111407_7fcb10e99eec4365af527f0bb3d27a0e.jpg");

    // when
    product.setName("바나나 스무디");
    productRepository.save(product);
    Optional<Product> foundProduct = productRepository.findById(product.getId());

    // then
    assertThat(foundProduct).isPresent();
    foundProduct.ifPresent(p -> {
      assertThat(p.getName()).isEqualTo("바나나 스무디");
    });
  }

  @Test
  public void testDeleteProduct() {
    // given
    Product product = createAndSaveProduct("딸기 아사이", 5900, "https://img1.kakaocdn.net/thumb/C320x320@2x.fwebp.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20231010111407_7fcb10e99eec4365af527f0bb3d27a0e.jpg");
    Long productId = product.getId();

    // when
    wishRepository.deleteAllByProductId(productId);
    productRepository.deleteById(productId);
    Optional<Product> foundProduct = productRepository.findById(productId);

    // then
    assertThat(foundProduct).isNotPresent();
  }

  @Test
  public void testFindAllWithPagination() {
    // given
    for (int i = 1; i <= 20; i++) {
      createAndSaveProduct("Product " + i, 1000 + i, "http://example.com/image" + i + ".jpg");
    }

    // when
    Pageable pageable = PageRequest.of(0, 10);
    Page<Product> productPage = productRepository.findAll(pageable);

    // then
    assertThat(productPage.getContent()).isNotEmpty();
    assertThat(productPage.getContent().size()).isEqualTo(10);
    assertThat(productPage.getTotalElements()).isEqualTo(20);
    assertThat(productPage.getTotalPages()).isEqualTo(2);

    // when
    Pageable secondPageable = PageRequest.of(1, 10);
    Page<Product> secondProductPage = productRepository.findAll(secondPageable);

    // then
    assertThat(secondProductPage.getContent()).isNotEmpty();
    assertThat(secondProductPage.getContent().size()).isEqualTo(10);
    assertThat(secondProductPage.getTotalElements()).isEqualTo(20);
    assertThat(secondProductPage.getTotalPages()).isEqualTo(2);
  }
}
