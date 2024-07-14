package gift.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import gift.entity.Product;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
public class ProductRepositoryTest {

  @Autowired
  private ProductRepository productRepository;

  @Test
  public void testSaveAndFindProduct() {
    Product product = new Product();
    product.setName("딸기 아사이");
    product.setPrice(5900);
    product.setImageUrl("https://img1.kakaocdn.net/thumb/C320x320@2x.fwebp.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20231010111407_7fcb10e99eec4365af527f0bb3d27a0e.jpg");

    productRepository.save(product);

    Optional<Product> foundProduct = productRepository.findById(product.getId());

    foundProduct.ifPresent(p -> {
      assertThat(p.getName()).isEqualTo("딸기 아사이");
      assertThat(p.getPrice()).isEqualTo(5900);
      assertThat(p.getImageUrl()).isEqualTo("https://img1.kakaocdn.net/thumb/C320x320@2x.fwebp.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20231010111407_7fcb10e99eec4365af527f0bb3d27a0e.jpg");
    });

    assertThat(foundProduct).isPresent();
  }

  @Test
  public void testUpdateProduct() {
    Product product = new Product();
    product.setName("딸기 아사이");
    product.setPrice(5900);
    product.setImageUrl("https://img1.kakaocdn.net/thumb/C320x320@2x.fwebp.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20231010111407_7fcb10e99eec4365af527f0bb3d27a0e.jpg");

    productRepository.save(product);

    product.setName("바나나 스무디");
    productRepository.save(product);

    Optional<Product> foundProduct = productRepository.findById(product.getId());

    foundProduct.ifPresent(p -> {
      assertThat(p.getName()).isEqualTo("바나나 스무디");
    });

    assertThat(foundProduct).isPresent();
  }

  @Test
  public void testDeleteProduct() {
    Product product = new Product();
    product.setName("딸기 아사이");
    product.setPrice(5900);
    product.setImageUrl("https://img1.kakaocdn.net/thumb/C320x320@2x.fwebp.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20231010111407_7fcb10e99eec4365af527f0bb3d27a0e.jpg");

    productRepository.save(product);
    Long productId = product.getId();

    productRepository.deleteById(productId);

    Optional<Product> foundProduct = productRepository.findById(productId);

    assertThat(foundProduct).isNotPresent();
  }

}
