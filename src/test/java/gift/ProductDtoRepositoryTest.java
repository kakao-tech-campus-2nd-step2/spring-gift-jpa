package gift;

import gift.Entity.Product;
import gift.Mapper.Mapper;
import gift.Model.ProductDto;
import gift.Repository.ProductJpaRepository;
import gift.Service.MemberService;
import gift.Service.ProductService;
import gift.Service.WishlistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductDtoRepositoryTest {

    @Autowired
    private ProductJpaRepository productJpaRepository;

    private Mapper mapper;

    @BeforeEach
    void setUp() {
        // 필요한 서비스의 목 객체 생성
        ProductService productService = Mockito.mock(ProductService.class);
        MemberService memberService = Mockito.mock(MemberService.class);
        WishlistService wishListService = Mockito.mock(WishlistService.class);

        // Mapper 인스턴스 수동 생성 및 주입
        mapper = new Mapper(productService, memberService, wishListService);
    }

    @Test
    public void testGetAllProducts() {
        ProductDto productDto1 = new ProductDto(1L, "productDto1", 1000, "http://localhost:8080/image1.jpg", false);
        Product product = mapper.productDtoToEntity(productDto1);
        productJpaRepository.save(product);

        ProductDto productDto2 = new ProductDto(2L, "productDto2", 2000, "http://localhost:8080/image2.jpg", false);
        Product product2 = mapper.productDtoToEntity(productDto2);

        productJpaRepository.save(product2);

        List<Product> productslist = productJpaRepository.findByisDeletedFalse();

        assertThat(productslist.get(0).getId()).isEqualTo(product.getId());
        assertThat(productslist.get(0).getName()).isEqualTo(product.getName());
        assertThat(productslist.get(0).getPrice()).isEqualTo(product.getPrice());
        assertThat(productslist.get(0).getImageUrl()).isEqualTo(product.getImageUrl());
        assertThat(productslist.get(0).isDeleted()).isEqualTo(product.isDeleted());

        assertThat(productslist.get(1).getId()).isEqualTo(product2.getId());
        assertThat(productslist.get(1).getName()).isEqualTo(product2.getName());
        assertThat(productslist.get(1).getPrice()).isEqualTo(product2.getPrice());
        assertThat(productslist.get(1).getImageUrl()).isEqualTo(product2.getImageUrl());
        assertThat(productslist.get(1).isDeleted()).isEqualTo(product2.isDeleted());

    }

    @Test
    public void testGetProductById() {
        ProductDto productDto1 = new ProductDto(1L, "productDto1", 1000, "http://localhost:8080/image1.jpg", false);
        Product product = mapper.productDtoToEntity(productDto1);

        Product savedProduct = productJpaRepository.save(product);

        assertThat(savedProduct.getId()).isEqualTo(product.getId());
        assertThat(savedProduct.getName()).isEqualTo(product.getName());
        assertThat(savedProduct.getPrice()).isEqualTo(product.getPrice());
        assertThat(savedProduct.getImageUrl()).isEqualTo(product.getImageUrl());
        assertThat(savedProduct.isDeleted()).isEqualTo(product.isDeleted());

    }

    @Test
    public void testSaveProduct() {
        ProductDto productDto1 = new ProductDto(1L, "productDto1", 1000, "http://localhost:8080/image1.jpg", false);
        Product product = mapper.productDtoToEntity(productDto1);

        Product savedProduct = productJpaRepository.save(product);

        assertThat(savedProduct.getName()).isEqualTo(product.getName());
        assertThat(savedProduct.getPrice()).isEqualTo(product.getPrice());

    }

    @Test
    public void testUpdateProduct() {
        ProductDto productDto1 = new ProductDto(1L, "productDto1", 1000, "http://localhost:8080/image1.jpg", false);
        Product product1 = mapper.productDtoToEntity(productDto1);

        productJpaRepository.save(product1);

        product1.setName("productDto2");
        product1.setPrice(2000);
        product1.setImageUrl("http://localhost:8080/image2.jpg");

        Product product2 = product1;

        Product savedProduct = productJpaRepository.save(product2);

        // product1을 update한 후 product2로 저장한 것이므로 product1의 id와 product2의 id는 같아야 한다.
        assertThat(savedProduct.getId()).isEqualTo(product1.getId());

    }

    @Test
    public void testDeleteProduct() {
        ProductDto productDto1 = new ProductDto(1L, "productDto1", 1000, "http://localhost:8080/image1.jpg", false);
        Product product = mapper.productDtoToEntity(productDto1);

        productJpaRepository.save(product);

        productJpaRepository.delete(product);

        assertThat(productJpaRepository.findById(product.getId())).isEmpty();
    }
}
