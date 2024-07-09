package gift.service;

import gift.product.exception.DuplicateException;
import gift.product.exception.InstanceValueException;
import gift.product.exception.InvalidProductIdException;
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
        System.out.println("[ProductServiceTest] testRegisterNormalProduct()");
        Product normalProduct = new Product(1L, "normal", 1000, "image.url");
        System.out.println(normalProduct.getId()+ " " + normalProduct.getName() + " " + normalProduct.getPrice() + " " + normalProduct.getImageUrl());
        productService.registerProduct(normalProduct);
    }

    @Test
    void testRegisterDuplicateId() {
        System.out.println("[ProductServiceTest] testRegisterDuplicateId()");
        Product product = new Product(2L, "normal", 1000, "image.url");
        productService.registerProduct(product);
        Product duplicateIdProduct = new Product(product.getId(), "duplicateID", 1000, "image.url");
        Assertions.assertThrows(DuplicateException.class, () -> {
            productService.registerProduct(duplicateIdProduct);
        });
    }

    @Test
    void testRegisterIncludeKaKao() {
        System.out.println("[ProductServiceTest] testRegisterIncludeKaKao()");
        Product product = new Product(3L, "카카오프렌즈", 5000, "image.url");
        Assertions.assertThrows(InvalidProductNameException.class, () -> {
            productService.registerProduct(product);
        });
    }

    @Test
    void testRegisterNegativePrice() {
        System.out.println("[ProductServiceTest] testRegisterNegativePrice()");
        Product freeProduct = new Product(4L, "free", -1, "image.url");
        Assertions.assertThrows(InstanceValueException.class, () -> {
            productService.registerProduct(freeProduct);
        });
    }

    @Test
    void testRegisterNullInstance() {
        System.out.println("[ProductServiceTest] testRegisterNullInstance()");
        Product nullImageUrlProduct = new Product(5L, "nullImageUrl", 1000, null);
        Assertions.assertThrows(InstanceValueException.class, () -> {
            productService.registerProduct(nullImageUrlProduct);
        });
    }

    @Test
    void testUpdateProduct() {
        System.out.println("[ProductServiceTest] testUpdateProduct()");
        Product product = new Product(6L, "originalProduct", 1000, "image.url");
        productService.registerProduct(product);
        Product updateProduct = new Product(product.getId(), "updateProduct", 2000, "image.url");
        productService.updateProduct(updateProduct);
    }

    @Test
    void testUpdateWrongId() {
        System.out.println("[ProductServiceTest] testUpdateWrongId()");
        Product product = new Product(7L, "product", 1000, "image.url");
        productService.registerProduct(product);
        Product updateProduct = new Product(0L, product.getName(), product.getPrice(), product.getImageUrl());
        Assertions.assertThrows(InvalidProductIdException.class, () -> {
            productService.updateProduct(updateProduct);
        });
    }

    @Test
    void testUpdateInvalidNameProduct() {
        System.out.println("[ProductServiceTest] testUpdateInvalidNameProduct()");
        Product product = new Product(8L, "product", 1000, "image.url");
        productService.registerProduct(product);
        Product updateProduct = new Product(product.getId(), "카카오프렌즈", product.getPrice(), product.getImageUrl());
        Assertions.assertThrows(InstanceValueException.class, () -> {
            productService.updateProduct(updateProduct);
        });
    }

    @Test
    void testUpdateNegativePriceProduct() {
        System.out.println("[ProductServiceTest] testUpdateNegativePriceProduct()");
        Product product = new Product(9L, "originalProduct", 1000, "image.url");
        productService.registerProduct(product);
        Product updateProduct = new Product(product.getId(), product.getName(), -1, product.getImageUrl());
        Assertions.assertThrows(InstanceValueException.class, () -> {
            productService.updateProduct(updateProduct);
        });
    }

}