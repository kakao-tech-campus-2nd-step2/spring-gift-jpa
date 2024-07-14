package gift;

import gift.product.repository.ProductRepository;
import gift.product.entity.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ProductRepositoryTest {

    private ProductRepository productRepository;

    @Autowired
    public ProductRepositoryTest(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Test
    public void createTest() {
        Product product = new Product("물감", 3000, "mulgam.png");
        Product nullProduct = new Product(null, 0, null);

        Product actual = productRepository.save(product);

        // 주소값을 비교했을 때 동일해야 함. (동일성 보장)
        Assertions.assertThat(actual == product).isTrue();
        // id가 JPA의 생성전략에 의해 생성된 상태여야 함.
        Assertions.assertThat(actual.getProductId()).isNotNull();
        // null을 넣으면 null 제약 조건에 위배 되어야 함.
        Assertions.assertThatCode(() -> {
            productRepository.save(nullProduct);
        }).isInstanceOf(Exception.class);
    }

    @Test
    public void readTest() {
        Product product1 = new Product("물감", 3000, "mulgam.png");
        Product product2 = new Product("붓", 1000, "but.png");

        productRepository.save(product1);
        productRepository.save(product2);

        // 크기가 2이어야 함.
        Assertions.assertThat(productRepository.count()).isEqualTo(2);
        // 제품들을 조회
        Assertions.assertThat(productRepository.findAll()).contains(product1).contains(product2);
        // 없는 제품을 조회하면 빈 리스트가 반환
        Assertions.assertThat(productRepository.findByName("크레파스")).size().isEqualTo(0);
    }

    @Test
    public void UpdateTest() {
        Product product = new Product("물감", 3000, "mulgam.png");

        Product actual = productRepository.save(product);
        actual.updateProduct("페인트", 10000, "paint.png");

        // 더티 체킹에 의해 DB에도 업데이트 쿼리가 적용되어야 함.
        Assertions.assertThat(productRepository.existsByName("페인트")).isTrue();
    }

    @Test
    public void deleteTest() {
        Product product = new Product("물감", 3000, "mulgam.png");

        Product actual = productRepository.save(product);
        productRepository.delete(actual);

        // 삽입 후 다시 삭제하면 크기가 0이어야 함.
        Assertions.assertThat(productRepository.count()).isEqualTo(0);
    }
}
