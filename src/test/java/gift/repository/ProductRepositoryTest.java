package gift.repository;

import static org.assertj.core.api.Assertions.assertThat;

import gift.product.entity.Product;
import gift.product.repository.ProductRepository;
import java.util.Optional;
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

  @Test
  public void testFindAllWithPagination() {
    for (int i = 1; i <= 20; i++) {
      Product product = new Product();
      product.setName("Product " + i);
      product.setPrice(1000 + i);
      product.setImageUrl("http://example.com/image" + i + ".jpg");
      productRepository.save(product);
    }

    Pageable pageable = PageRequest.of(0, 10);
    Page<Product> productPage = productRepository.findAll(pageable);

    assertThat(productPage.getContent()).isNotEmpty();
    assertThat(productPage.getContent().size()).isEqualTo(10);
    assertThat(productPage.getTotalElements()).isEqualTo(20);
    assertThat(productPage.getTotalPages()).isEqualTo(2);

    Pageable secondPageable = PageRequest.of(1, 10);
    Page<Product> secondProductPage = productRepository.findAll(secondPageable);

    assertThat(secondProductPage.getContent()).isNotEmpty();
    assertThat(secondProductPage.getContent().size()).isEqualTo(10);
    assertThat(secondProductPage.getTotalElements()).isEqualTo(20);
    assertThat(secondProductPage.getTotalPages()).isEqualTo(2);
  }
}
