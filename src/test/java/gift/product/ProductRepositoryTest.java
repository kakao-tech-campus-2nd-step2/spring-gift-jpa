package gift.product;

import static org.assertj.core.api.Assertions.assertThat;

import gift.entity.ProductEntity;
import gift.repository.ProductRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

//    @BeforeEach
//    public void setUp() {
//        productRepository.save(new ProductEntity("콜라", 1500L, "https://image1.jpg"));
//        productRepository.save(new ProductEntity("레모네이드", 2500L, "https://image2.jpg"));
//        productRepository.save(new ProductEntity("밀크쉐이크", 3000L, "https://image3.jpg"));
//    }

    @Test
    @DisplayName("상품 추가 기능 확인")
    void save() {
        //given
        ProductEntity expectedEntity = new ProductEntity("아이스 아메리카노", 4500L, "https://image1.jpg");

        //when
        ProductEntity actualEntity = productRepository.save(expectedEntity);

        //then
        assertThat(actualEntity.getId()).isNotNull();
        assertThat(actualEntity.getName()).isEqualTo(expectedEntity.getName());
        assertThat(actualEntity.getPrice()).isEqualTo(expectedEntity.getPrice());
        assertThat(actualEntity.getImageUrl()).isEqualTo(expectedEntity.getImageUrl());
    }

    @Test
    @DisplayName("상품 이름으로 검색")
    void findByNameContaining() {
        //given
        ProductEntity productEntity1 = new ProductEntity("아이스 아메리카노", 4500L, "https://image4.jpg");
        ProductEntity productEntity2 = new ProductEntity("핫 아메리카노", 4500L, "https://image5.jpg");
        String name = "아메리카노";

        //when
        productRepository.save(productEntity1);
        productRepository.save(productEntity2);

        List<ProductEntity> list = productRepository.findByNameContaining(name);

        //then
        assertThat(list.size()).isEqualTo(2);
        assertThat(list.get(0).getName()).isEqualTo(productEntity1.getName());
        assertThat(list.get(1).getName()).isEqualTo(productEntity2.getName());
    }

    @Test
    @DisplayName("상품 전체 조회 기능 확인")
    void findAll() {
        //given
        ProductEntity productEntity1 = new ProductEntity("아이스 아메리카노", 4500L, "https://image4.jpg");
        ProductEntity productEntity2 = new ProductEntity("핫 아메리카노", 4500L, "https://image5.jpg");
        ProductEntity productEntity3 = new ProductEntity("밀크쉐이크", 3000L, "https://image3.jpg");

        //when
        productRepository.save(productEntity1);
        productRepository.save(productEntity2);
        productRepository.save(productEntity3);
        List<ProductEntity> list = productRepository.findAll();

        //then
        assertThat(list.size()).isEqualTo(3);
        assertThat(list.get(0).getName()).isEqualTo(productEntity1.getName());
        assertThat(list.get(1).getName()).isEqualTo(productEntity2.getName());
        assertThat(list.get(2).getName()).isEqualTo(productEntity3.getName());
    }

}
