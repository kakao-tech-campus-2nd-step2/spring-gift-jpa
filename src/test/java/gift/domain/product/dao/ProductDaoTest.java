package gift.domain.product.dao;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import gift.domain.product.entity.Product;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ProductDaoTest {

    @Autowired
    private ProductDao productDao;


    @Test
    @DisplayName("DB 상품 추가")
    void insert() {
        // given
        Product product = new Product(null, "탕종 블루베리 베이글", 3500, "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg");

        // when
        Product savedProduct = productDao.insert(product);

        // then
        assertAll(
            () -> assertThat(savedProduct).isNotNull(),
            () -> assertThat(savedProduct.getId()).isNotNull(),
            () -> assertThat(savedProduct.getName()).isEqualTo(product.getName()),
            () -> assertThat(savedProduct.getPrice()).isEqualTo(product.getPrice()),
            () -> assertThat(savedProduct.getImageUrl()).isEqualTo(product.getImageUrl())
        );
    }

    /**
     * 편의 상 데이터 2개에 대해서만 검증
     */
    @Test
    @DisplayName("DB 전체 상품 조회")
    void findAll() {
        // given
        Product product1 = new Product(null, "아이스 카페 아메리카노 T", 4500, "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110563]_20210426095937947.jpg");
        productDao.insert(product1);
        Product product2 = new Product(null, "아이스 카페 라떼 T", 5000, "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110569]_20210415143036138.jpg");
        productDao.insert(product2);

        // when
        List<Product> productList = productDao.findAll();

        // then
        assertAll(
            () -> assertThat(productList.get(0)).isNotNull(),
            () -> assertThat(productList.get(0).getId()).isNotNull(),
            () -> assertThat(productList.get(0).getName()).isEqualTo("아이스 카페 아메리카노 T"),
            () -> assertThat(productList.get(0).getPrice()).isEqualTo(4500),
            () -> assertThat(productList.get(0).getImageUrl()).isEqualTo("https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110563]_20210426095937947.jpg"),
            () -> assertThat(productList.get(1)).isNotNull(),
            () -> assertThat(productList.get(1).getId()).isNotNull(),
            () -> assertThat(productList.get(1).getName()).isEqualTo("아이스 카페 라떼 T"),
            () -> assertThat(productList.get(1).getPrice()).isEqualTo(5000),
            () -> assertThat(productList.get(1).getImageUrl()).isEqualTo("https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[110569]_20210415143036138.jpg")
        );
    }

    @Test
    @DisplayName("DB 특정 상품 조회")
    void findById() {
        // given
        Product product = new Product(null, "탕종 블루베리 베이글", 3500, "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg");
        Product savedProduct = productDao.insert(product);

        // when
        Product foundProduct = productDao.findById(savedProduct.getId()).get();

        // then
        assertAll(
            () -> assertThat(foundProduct).isNotNull(),
            () -> assertThat(foundProduct.getId()).isNotNull(),
            () -> assertThat(foundProduct.getName()).isEqualTo(product.getName()),
            () -> assertThat(foundProduct.getPrice()).isEqualTo(product.getPrice()),
            () -> assertThat(foundProduct.getImageUrl()).isEqualTo(product.getImageUrl())
        );
    }

    @Test
    @DisplayName("DB 상품 수정")
    void update() {
        // given
        Product product1 = new Product(null, "탕종 블루베리 베이글", 3500, "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg");
        Product savedProduct = productDao.insert(product1);
        Product product2 = new Product(savedProduct.getId(), "오트 콜드 브루", 5800, "https://image.istarbucks.co.kr/upload/store/skuimg/2021/04/[9200000003285]_20210416154437069.jpg");

        // when
        Product updatedProduct = productDao.update(product2).get();

        // then
        assertAll(
            () -> assertThat(updatedProduct).isNotNull(),
            () -> assertThat(updatedProduct.getId()).isEqualTo(savedProduct.getId()),
            () -> assertThat(updatedProduct.getName()).isEqualTo(product2.getName()),
            () -> assertThat(updatedProduct.getPrice()).isEqualTo(product2.getPrice()),
            () -> assertThat(updatedProduct.getImageUrl()).isEqualTo(product2.getImageUrl())
        );
    }

    @Test
    @DisplayName("DB 상품 삭제")
    void delete() {
        // given
        Product product1 = new Product(null, "탕종 블루베리 베이글", 3500, "https://image.istarbucks.co.kr/upload/store/skuimg/2023/09/[9300000004823]_20230911131337469.jpg");
        Product savedProduct = productDao.insert(product1);

        // when
        productDao.delete(savedProduct.getId());

        // then
        assertThat(productDao.findById(savedProduct.getId())).isEmpty();
    }
}
