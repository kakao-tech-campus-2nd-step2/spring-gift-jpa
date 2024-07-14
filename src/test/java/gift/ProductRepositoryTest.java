package gift;

import gift.Entity.Products;
import gift.Model.Product;
import gift.Repository.ProductJpaRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Transactional
public class ProductRepositoryTest {

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Test
    public void testGetAllProducts() {
        Product product1 = new Product();
        product1.setName("product1");
        product1.setPrice(1000);
        product1.setImageUrl("http://localhost:8080/image1.jpg");
        Products products = Products.createProducts(product1);

        productJpaRepository.save(products);

        Product product2 = new Product();
        product2.setName("product2");
        product2.setPrice(2000);
        product2.setImageUrl("http://localhost:8080/image2.jpg");
        Products products2 = Products.createProducts(product2);

        productJpaRepository.save(products2);

        List<Products> productslist = productJpaRepository.findByisDeletedFalse();

        assertThat(productslist).contains(products, products2);

    }

    @Test
    public void testGetProductById() { //이거 UserRepositoryTest보고 수정하기!!!!!
        Product product1 = new Product();
        product1.setName("product1");
        product1.setPrice(1000);
        product1.setImageUrl("http://localhost:8080/image1.jpg");
        Products products = Products.createProducts(product1);

        productJpaRepository.save(products);

        assertThat(productJpaRepository.findById(products.getId())).contains(products);

    }

    @Test
    public void testSaveProduct() {
        Product product1 = new Product();
        product1.setName("product1");
        product1.setPrice(1000);
        product1.setImageUrl("http://localhost:8080/image1.jpg");
        Products products = Products.createProducts(product1);

        Products savedProducts = productJpaRepository.save(products);

        assertThat(savedProducts.getName()).isEqualTo(products.getName());
        assertThat(savedProducts.getPrice()).isEqualTo(products.getPrice());

    }

    @Test
    public void testUpdateProduct() {
        Product product1 = new Product();
        product1.setName("product1");
        product1.setPrice(1000);
        product1.setImageUrl("http://localhost:8080/image1.jpg");
        Products products = Products.createProducts(product1);

        productJpaRepository.save(products);

        products.setName("product2");
        products.setPrice(2000);
        products.setImageUrl("http://localhost:8080/image2.jpg");

        Products products2 = products;

        productJpaRepository.save(products2);

        // products의 Id로 Entity를 가져왔을 때 그것이 products2와 같다면 true
        assertThat(productJpaRepository.findById(products.getId())).contains(products2);

    }

    @Test
    public void testDeleteProduct() {
        Product product1 = new Product();
        product1.setName("product1");
        product1.setPrice(1000);
        product1.setImageUrl("http://localhost:8080/image1.jpg");
        Products products = Products.createProducts(product1);

        productJpaRepository.save(products);

        productJpaRepository.delete(products);

        assertThat(productJpaRepository.findById(products.getId())).isEmpty();
    }
}
