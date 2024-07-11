package gift.service;

import gift.dto.ProductRequestDto;
import gift.dto.ProductResponseDto;
import gift.dto.WishRequestDto;
import gift.dto.WishResponseDto;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

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
    public void 위시리스트_추가() {
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
    public void 위시리스트_조회() {
        User user = userRepository.save(new User("user@example.com", "password"));
        Product product = productRepository.save(new Product(new ProductName("오둥이 입니다만"), 29800, "https://example.com/product1.jpg"));
        wishRepository.save(new Wish(user, product));

        Pageable pageable = PageRequest.of(0, 10);
        Page<WishResponseDto> wishList = wishService.getWishesByUserId(user.getId(), pageable);

        assertNotNull(wishList);
        assertEquals(1, wishList.getTotalElements());
        WishResponseDto retrievedWish = wishList.getContent().get(0);
        assertEquals(product.getId(), retrievedWish.getProductId());
        assertEquals(product.getName().getValue(), retrievedWish.getProductName());
        assertEquals(product.getPrice(), retrievedWish.getProductPrice());
        assertEquals(product.getImageUrl(), retrievedWish.getProductImageUrl());
    }

    @Test
    @Rollback
    public void 위시리스트_삭제() {
        User user = userRepository.save(new User("user@example.com", "password"));
        Product product = productRepository.save(new Product(new ProductName("오둥이 입니다만"), 29800, "https://example.com/product1.jpg"));
        Wish wish = wishRepository.save(new Wish(user, product));

        wishService.deleteWish(wish.getId());

        Pageable pageable = PageRequest.of(0, 10);
        Page<WishResponseDto> wishList = wishService.getWishesByUserId(user.getId(), pageable);
        assertTrue(wishList.isEmpty());
    }

    @Test
    @Rollback
    public void 위시리스트_삭제_없는위시() {
        assertThrows(BusinessException.class, () -> wishService.deleteWish(999L));
    }

    @Test
    @Rollback
    public void 위시리스트_목록_페이지네이션() {
        User user = userRepository.save(new User("user@example.com", "password"));
        for (int i = 1; i <= 25; i++) {
            Product product = productRepository.save(new Product(new ProductName("상품 " + i), 10000, "https://example.com/product" + i + ".jpg"));
            wishRepository.save(new Wish(user, product));
        }

        Pageable pageable = PageRequest.of(0, 10);
        Page<WishResponseDto> wishList = wishService.getWishesByUserId(user.getId(), pageable);

        assertNotNull(wishList);
        assertEquals(10, wishList.getSize());
        assertEquals(25, wishList.getTotalElements());
        assertEquals(3, wishList.getTotalPages());
    }
}
