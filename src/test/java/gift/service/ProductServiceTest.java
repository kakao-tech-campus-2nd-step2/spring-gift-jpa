package gift.service;

import gift.dto.ProductRequestDto;
import gift.dto.ProductResponseDto;
import gift.entity.Product;
import gift.entity.ProductName;
import gift.exception.BusinessException;
import gift.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @AfterEach
    public void 데이터_정리() {
        productRepository.deleteAll();
    }

    @Test
    @Rollback
    public void 상품_추가() {
        ProductRequestDto requestDTO = new ProductRequestDto("아이스 카페 아메리카노 T", 4500, "https://example.com/product1.jpg");
        ProductResponseDto createdProduct = productService.addProduct(requestDTO);

        assertNotNull(createdProduct);
        assertNotNull(createdProduct.getId());
        assertEquals("아이스 카페 아메리카노 T", createdProduct.getName());
        assertEquals(4500, createdProduct.getPrice());
        assertEquals("https://example.com/product1.jpg", createdProduct.getImageUrl());
    }

    @Test
    @Rollback
    public void 상품_조회() {
        Product product = new Product(new ProductName("오둥이 입니다만"), 29800, "https://example.com/product2.jpg");
        productRepository.save(product);

        List<ProductResponseDto> productList = productService.getAllProducts(PageRequest.of(0, 10)).getContent();

        assertNotNull(productList);
        assertEquals(1, productList.size());
        ProductResponseDto retrievedProduct = productList.get(0);
        assertEquals("오둥이 입니다만", retrievedProduct.getName());
        assertEquals(29800, retrievedProduct.getPrice());
        assertEquals("https://example.com/product2.jpg", retrievedProduct.getImageUrl());
    }

    @Test
    @Rollback
    public void 상품_수정() {
        Product originalProduct = new Product(new ProductName("오둥이 입니다만"), 29800, "https://example.com/product2.jpg");
        productRepository.save(originalProduct);

        ProductRequestDto updateDTO = new ProductRequestDto("오둥이 아닙니다만", 35000, "https://example.com/product3.jpg");
        ProductResponseDto result = productService.updateProduct(originalProduct.getId(), updateDTO);

        assertNotNull(result);
        assertEquals(originalProduct.getId(), result.getId());
        assertEquals("오둥이 아닙니다만", result.getName());
        assertEquals(35000, result.getPrice());
        assertEquals("https://example.com/product3.jpg", result.getImageUrl());
    }

    @Test
    @Rollback
    public void 상품_수정_없는상품() {
        ProductRequestDto updateDTO = new ProductRequestDto("오둥이 아닙니다만", 35000, "https://example.com/product3.jpg");

        assertThrows(BusinessException.class, () -> productService.updateProduct(100L, updateDTO));
    }

    @Test
    @Rollback
    public void 상품_삭제() {
        Product product = new Product(new ProductName("오둥이 입니다만"), 29800, "https://example.com/product2.jpg");
        productRepository.save(product);

        productService.deleteProduct(product.getId());

        List<ProductResponseDto> productList = productService.getAllProducts(PageRequest.of(0, 10)).getContent();
        assertTrue(productList.isEmpty());
    }

    @Test
    @Rollback
    public void 상품_삭제_없는상품() {
        assertThrows(BusinessException.class, () -> productService.deleteProduct(2L));
    }

    @Test
    @Rollback
    public void 상품_목록_페이지네이션() {
        for (int i = 1; i <= 25; i++) {
            Product product = new Product(new ProductName("상품 " + i), 1000 + i, "https://example.com/product" + i + ".jpg");
            productRepository.save(product);
        }

        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductResponseDto> page1 = productService.getAllProducts(pageable);

        assertNotNull(page1);
        assertEquals(10, page1.getNumberOfElements());
        assertEquals(0, page1.getNumber());
        assertEquals(3, page1.getTotalPages());

        pageable = PageRequest.of(1, 10);
        Page<ProductResponseDto> page2 = productService.getAllProducts(pageable);

        assertNotNull(page2);
        assertEquals(10, page2.getNumberOfElements());
        assertEquals(1, page2.getNumber());

        pageable = PageRequest.of(2, 10);
        Page<ProductResponseDto> page3 = productService.getAllProducts(pageable);

        assertNotNull(page3);
        assertEquals(5, page3.getNumberOfElements());
        assertEquals(2, page3.getNumber());
    }
}
