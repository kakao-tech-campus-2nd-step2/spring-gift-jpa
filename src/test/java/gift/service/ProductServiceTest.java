package gift.service;

import gift.product.dao.ProductDao;
import gift.product.exception.InstanceValueException;
import gift.product.exception.InvalidProductIdException;
import gift.product.model.Product;
import gift.product.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductServiceTest {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductDao productDao;

    @Test
    void testRegisterNormalProduct() {
        System.out.println("[ProductServiceTest] testRegisterNormalProduct()");
        Product normalProduct = new Product("normalProduct", 1000, "image.url");
        productService.registerProduct(normalProduct);
    }

    @Test
    void testRegisterIncludeKaKao() {
        System.out.println("[ProductServiceTest] testRegisterIncludeKaKao()");
        Product product = new Product("카카오프렌즈", 5000, "image.url");
        Assertions.assertThrows(InstanceValueException.class, () -> {
            productService.registerProduct(product);
        });
    }

    @Test
    void testRegisterNegativePrice() {
        System.out.println("[ProductServiceTest] testRegisterNegativePrice()");
        Product freeProduct = new Product("free", -1, "image.url");
        Assertions.assertThrows(InstanceValueException.class, () -> {
            productService.registerProduct(freeProduct);
        });
    }

    @Test
    void testRegisterNullInstance() {
        System.out.println("[ProductServiceTest] testRegisterNullInstance()");
        Product nullImageUrlProduct = new Product("nullImageUrl", 1000, null);
        Assertions.assertThrows(InstanceValueException.class, () -> {
            productService.registerProduct(nullImageUrlProduct);
        });
    }

    @Test
    void testUpdateProduct() {
        System.out.println("[ProductServiceTest] testUpdateProduct()");
        Product product = productDao.save(
                new Product(
                        "originalProduct",
                        1000,
                        "image.url"
                )
        );
        Product updateProduct = new Product(
            "updateProduct",
            2000,
            "updateImage.url");
        productService.updateProduct(product.getId(), updateProduct);
    }

    @Test
    void testUpdateNotExistId() {
        System.out.println("[ProductServiceTest] testUpdateNotExistId()");
        Product product = new Product(
                "originalProduct",
                1000,
                "image.url"
        );
        productDao.save(product);
        Assertions.assertThrows(InvalidProductIdException.class, () -> {
            productService.updateProduct(-1L, product);
        });
    }

    @Test
    void testUpdateInvalidNameProduct() {
        System.out.println("[ProductServiceTest] testUpdateInvalidNameProduct()");
        Product product = productDao.save(
                new Product(
                        "originalProduct",
                        1000,
                        "image.url"
                )
        );
        Product updateProduct = new Product("카카오프렌즈", product.getPrice(), product.getImageUrl());
        Assertions.assertThrows(InstanceValueException.class, () -> {
            productService.updateProduct(product.getId(), updateProduct);
        });
    }

    @Test
    void testUpdateNegativePriceProduct() {
        System.out.println("[ProductServiceTest] testUpdateNegativePriceProduct()");
        Product product = productDao.save(
                new Product(
                        "originalProduct",
                        1000,
                        "image.url"
                )
        );
        Product updateProduct = new Product(product.getName(), -1, product.getImageUrl());
        Assertions.assertThrows(InstanceValueException.class, () -> {
            productService.updateProduct(product.getId(), updateProduct);
        });
    }

}