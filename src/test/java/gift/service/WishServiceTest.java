package gift.service;

import gift.dto.ProductResponseDto;
import gift.dto.WishRequestDto;
import gift.dto.WishResponseDto;
import gift.entity.Product;
import gift.entity.ProductName;
import gift.entity.Wish;
import gift.exception.BusinessException;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class WishServiceTest {

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private WishService wishService;

    @Test
    public void 위시리스트_추가() {
        Product product = new Product(new ProductName("오둥이 입니다만"), 29800, "https://example.com/product1.jpg");
        productRepository.save(product);
        ProductResponseDto productResponseDto = productService.getProductById(product.getId());

        WishRequestDto requestDto = new WishRequestDto(productResponseDto.getId());
        WishResponseDto createdWish = wishService.addWish(1L, requestDto);

        assertNotNull(createdWish);
        assertNotNull(createdWish.getId());
        assertEquals(productResponseDto.getId(), createdWish.getProductId());
        assertEquals(productResponseDto.getName(), createdWish.getProductName());
        assertEquals(productResponseDto.getPrice(), createdWish.getProductPrice());
        assertEquals(productResponseDto.getImageUrl(), createdWish.getProductImageUrl());
    }

    @Test
    public void 위시리스트_조회() {
        Product product = new Product(new ProductName("오둥이 입니다만"), 29800, "https://example.com/product1.jpg");
        productRepository.save(product);

        Wish wish = new Wish(1L, product.getId());
        wishRepository.save(wish);

        List<WishResponseDto> wishList = wishService.getWishesByUserId(1L);

        assertNotNull(wishList);
        assertEquals(1, wishList.size());
        WishResponseDto retrievedWish = wishList.get(0);
        assertEquals(product.getId(), retrievedWish.getProductId());
        assertEquals(product.getName().getValue(), retrievedWish.getProductName());
        assertEquals(product.getPrice(), retrievedWish.getProductPrice());
        assertEquals(product.getImageUrl(), retrievedWish.getProductImageUrl());
    }

    @Test
    public void 위시리스트_삭제() {
        Product product = new Product(new ProductName("오둥이 입니다만"), 29800, "https://example.com/product1.jpg");
        productRepository.save(product);

        Wish wish = new Wish(1L, product.getId());
        wishRepository.save(wish);

        wishService.deleteWish(wish.getId());

        List<WishResponseDto> wishList = wishService.getWishesByUserId(1L);
        assertTrue(wishList.isEmpty());
    }

    @Test
    public void 위시리스트_삭제_없는위시() {
        assertThrows(BusinessException.class, () -> wishService.deleteWish(999L));
    }
}
