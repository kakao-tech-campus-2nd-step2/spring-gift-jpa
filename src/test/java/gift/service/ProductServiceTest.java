package gift.service;

import gift.product.exception.DuplicateIdException;
import gift.product.exception.InstanceValueException;
import gift.product.exception.InvalidProductNameException;
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

    @Test
    void testRegisterNormalProduct() {
        Product normalProduct = new Product(1L, "normal", 1000, "image.url");
        System.out.println(normalProduct.getId()+ " " + normalProduct.getName() + " " + normalProduct.getPrice() + " " + normalProduct.getImageUrl());
        productService.registerProduct(normalProduct);
    }

    @Test
    void testRegisterDuplicateId() {
        Product product = new Product(2L, "normal", 1000, "image.url");
        productService.registerProduct(product);
        Product duplicateIdProduct = new Product(2L, "duplicateID", 1000, "image.url");
        Assertions.assertThrows(DuplicateIdException.class, () -> {
            productService.registerProduct(duplicateIdProduct);
        });
    }

    @Test
    void testRegisterIncludeKaKao() {
        Product product = new Product(3L, "카카오프렌즈", 5000, "image.url");
        Assertions.assertThrows(InvalidProductNameException.class, () -> {
            productService.registerProduct(product);
        });
    }

    @Test
    void testRegisterNegativePrice() {
        Product freeProduct = new Product(4L, "free", -1, "image.url");
        Assertions.assertThrows(InstanceValueException.class, () -> {
            productService.registerProduct(freeProduct);
        });
    }

    @Test
    void testRegisterNullInstance() {
        Product nullImageUrlProduct = new Product(5L, "nullImageUrl", 1000, null);
        Assertions.assertThrows(InstanceValueException.class, () -> {
            productService.registerProduct(nullImageUrlProduct);
        });
    }

}