package gift;

import static org.assertj.core.api.Assertions.assertThat;

import gift.product.Product;
import gift.product.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품 추가 테스트")
    void save(){
        //Given
        Product product = new Product(1000,"라이언","image.jpg");

        //When
        Product actual = productRepository.save(product);

        //Then
        assertThat(actual.getName()).isEqualTo("라이언");
        assertThat(actual.getPrice()).isEqualTo(1000);
        assertThat(actual.getImageUrl()).isEqualTo("image.jpg");
    }

    @Test
    @DisplayName("상품 아이디로 찾기 테스트")
    void findById() {
        //Given
        Product product = new Product(1000,"라이언","image.jpg");
        productRepository.save(product);

        //When
        Optional<Product> actual = productRepository.findById(product.getId());
        assertThat(actual).isPresent();

        //Then
        assertThat(actual.get().getName()).isEqualTo("라이언");
    }

    @Test
    @DisplayName("전체 상품 찾기 테스트")
    void findAll(){
        //Given
        Product product = new Product(1000,"라이언","image.jpg");
        productRepository.save(product);
        product = new Product(3000,"이춘식","example.jpg");
        productRepository.save(product);

        //When
        List<Product> actual = productRepository.findAll();

        //Then
        assertThat(actual.get(1).getId()).isNotNull();
        assertThat(actual.getFirst().getPrice()).isEqualTo(1000);
        assertThat(actual.getFirst().getName()).isEqualTo("라이언");
        assertThat(actual.getFirst().getImageUrl()).isEqualTo("image.jpg");
        assertThat(actual.get(1).getPrice()).isEqualTo(3000);
        assertThat(actual.get(1).getName()).isEqualTo("이춘식");
        assertThat(actual.get(1).getImageUrl()).isEqualTo("example.jpg");
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void deleteById() {
        //Given
        Product product = new Product(1000,"라이언","image.jpg");
        productRepository.save(product);

        //When
        productRepository.deleteById(product.getId());
        Optional<Product> actual = productRepository.findById(product.getId());

        //Then
        assertThat(actual).isNotPresent();
    }
}
