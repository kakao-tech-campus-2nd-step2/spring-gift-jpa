package gift.service;

import gift.dto.ProductResponseDto;
import gift.dto.WishRequestDto;
import gift.dto.WishResponseDto;
import gift.dto.WishPageResponseDto;
import gift.entity.Product;
import gift.entity.ProductName;
import gift.entity.User;
import gift.entity.Wish;
import gift.exception.BusinessException;
import gift.repository.ProductRepository;
import gift.repository.UserRepository;
import gift.repository.WishRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.DynamicTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WishServiceTest {

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WishService wishService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @AfterEach
    public void 데이터_정리() {
        wishRepository.deleteAll();
        productRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @Rollback
    public void 위시리스트_추가_성공() {
        User user = userRepository.save(new User("user@example.com", "password"));
        Product product = productRepository.save(new Product(new ProductName("오둥이 입니다만"), 29800, "https://example.com/product1.jpg"));
        ProductResponseDto productResponseDto = productService.getProductById(product.getId());

        WishRequestDto requestDto = new WishRequestDto(productResponseDto.getId());
        WishResponseDto createdWish = wishService.addWish(user.getId(), requestDto);

        assertNotNull(createdWish);
        assertNotNull(createdWish.getId());
        assertEquals(productResponseDto.getId(), createdWish.getProductId());
        assertEquals(productResponseDto.getName(), createdWish.getProductName());
        assertEquals(productResponseDto.getPrice(), createdWish.getProductPrice());
        assertEquals(productResponseDto.getImageUrl(), createdWish.getProductImageUrl());
    }

    @Test
    @Rollback
    public void 위시리스트_조회_성공() {
        User user = userRepository.save(new User("user@example.com", "password"));
        Product product = productRepository.save(new Product(new ProductName("오둥이 입니다만"), 29800, "https://example.com/product1.jpg"));
        wishRepository.save(new Wish(user, product));

        WishPageResponseDto wishPage = wishService.getWishesByUserId(user.getId(), PageRequest.of(0, 10));

        assertNotNull(wishPage);
        assertEquals(1, wishPage.getTotalItems());
        WishResponseDto retrievedWish = wishPage.getWishes().get(0);
        assertEquals(product.getId(), retrievedWish.getProductId());
        assertEquals(product.getName().getValue(), retrievedWish.getProductName());
        assertEquals(product.getPrice(), retrievedWish.getProductPrice());
        assertEquals(product.getImageUrl(), retrievedWish.getProductImageUrl());
    }

    @Test
    @Rollback
    public void 위시리스트_삭제_성공() {
        User user = userRepository.save(new User("user@example.com", "password"));
        Product product = productRepository.save(new Product(new ProductName("오둥이 입니다만"), 29800, "https://example.com/product1.jpg"));
        Wish wish = wishRepository.save(new Wish(user, product));

        wishService.deleteWish(wish.getId());

        WishPageResponseDto wishPage = wishService.getWishesByUserId(user.getId(), PageRequest.of(0, 10));
        assertTrue(wishPage.getWishes().isEmpty());
    }

    @Test
    @Rollback
    public void 위시리스트_삭제_없는위시_예외_발생() {
        assertThrows(BusinessException.class, () -> wishService.deleteWish(999L));
    }

    @TestFactory
    @Rollback
    public Collection<DynamicTest> 위시리스트_목록_페이지네이션_성공() {
        User user = userRepository.save(new User("user@example.com", "password"));
        for (int i = 1; i <= 25; i++) {
            Product product = productRepository.save(new Product(new ProductName("상품 " + i), 10000, "https://example.com/product" + i + ".jpg"));
            wishRepository.save(new Wish(user, product));
        }

        List<DynamicTest> dynamicTests = new ArrayList<>();

        dynamicTests.add(DynamicTest.dynamicTest("첫번째 페이지 조회", () -> {
            WishPageResponseDto wishPage = wishService.getWishesByUserId(user.getId(), PageRequest.of(0, 10));
            assertNotNull(wishPage);
            assertEquals(10, wishPage.getWishes().size());
            assertEquals(25, wishPage.getTotalItems());
            assertEquals(3, wishPage.getTotalPages());
        }));

        dynamicTests.add(DynamicTest.dynamicTest("두번째 페이지 조회", () -> {
            WishPageResponseDto wishPage2 = wishService.getWishesByUserId(user.getId(), PageRequest.of(1, 10));
            assertNotNull(wishPage2);
            assertEquals(10, wishPage2.getWishes().size());
            assertEquals(25, wishPage2.getTotalItems());
        }));

        dynamicTests.add(DynamicTest.dynamicTest("세번째 페이지 조회", () -> {
            WishPageResponseDto wishPage3 = wishService.getWishesByUserId(user.getId(), PageRequest.of(2, 10));
            assertNotNull(wishPage3);
            assertEquals(5, wishPage3.getWishes().size());
            assertEquals(25, wishPage3.getTotalItems());
        }));

        return dynamicTests;
    }
}
