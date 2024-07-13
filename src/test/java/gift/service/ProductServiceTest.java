package gift.service;

import gift.product.model.Product;
import gift.product.dto.ProductDTO;
import gift.product.repository.ProductRepository;
import gift.product.exception.InstanceValueException;
import gift.product.exception.InvalidIdException;
import gift.product.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

@SpringBootTest
public class ProductServiceTest {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @Test
    void testRegisterNormalProduct() {
        System.out.println("[ProductServiceTest] testRegisterNormalProduct()");
        ProductDTO normalProduct = new ProductDTO("normalProduct", 1000, "image.url");
        productService.registerProduct(normalProduct);
    }

    @Test
    void testRegisterIncludeKaKao() {
        System.out.println("[ProductServiceTest] testRegisterIncludeKaKao()");
        ProductDTO productDTO = new ProductDTO("카카오프렌즈", 5000, "image.url");
        Assertions.assertThrows(InstanceValueException.class, () -> {
            productService.registerProduct(productDTO);
        });
    }

    @Test
    void testRegisterNegativePrice() {
        System.out.println("[ProductServiceTest] testRegisterNegativePrice()");
        ProductDTO freeProduct = new ProductDTO("free", -1, "image.url");
        Assertions.assertThrows(InstanceValueException.class, () -> {
            productService.registerProduct(freeProduct);
        });
    }

    @Test
    void testRegisterNullInstance() {
        System.out.println("[ProductServiceTest] testRegisterNullInstance()");
        ProductDTO nullImageUrlProduct = new ProductDTO("nullImageUrl", 1000, null);
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            productService.registerProduct(nullImageUrlProduct);
        });
    }

    @Test
    void testUpdateProduct() {
        System.out.println("[ProductServiceTest] testUpdateProduct()");
        Product product = productRepository.save(
                new Product(
                        "originalProduct",
                        1000,
                        "image.url"
                )
        );
        ProductDTO updateProduct = new ProductDTO(
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
        productRepository.save(product);
        ProductDTO productDTO = new ProductDTO(
            product.getName(),
            product.getPrice(),
            product.getImageUrl()
        );
        Assertions.assertThrows(InvalidIdException.class, () -> {
            productService.updateProduct(-1L, productDTO);
        });
    }

    @Test
    void testUpdateInvalidNameProduct() {
        System.out.println("[ProductServiceTest] testUpdateInvalidNameProduct()");
        Product product = productRepository.save(
                new Product(
                        "originalProduct",
                        1000,
                        "image.url"
                )
        );
        ProductDTO updateProduct = new ProductDTO("카카오프렌즈", product.getPrice(), product.getImageUrl());
        Assertions.assertThrows(InstanceValueException.class, () -> {
            productService.updateProduct(product.getId(), updateProduct);
        });
    }

    @Test
    void testUpdateNegativePriceProduct() {
        System.out.println("[ProductServiceTest] testUpdateNegativePriceProduct()");
        Product product = productRepository.save(
                new Product(
                        "originalProduct",
                        1000,
                        "image.url"
                )
        );
        ProductDTO updateProduct = new ProductDTO(product.getName(), -1, product.getImageUrl());
        Assertions.assertThrows(InstanceValueException.class, () -> {
            productService.updateProduct(product.getId(), updateProduct);
        });
    }

}