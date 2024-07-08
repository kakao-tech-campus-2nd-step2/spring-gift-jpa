package gift.service;

import gift.dto.WishRequestDto;
import gift.dto.WishResponseDto;
import gift.entity.Wish;
import gift.entity.WishDao;
import gift.exception.BusinessException;
import gift.dto.ProductResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class WishServiceTest {

    @Mock
    private WishDao wishDao;

    @Mock
    private ProductService productService;

    @InjectMocks
    private WishService wishService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void 위시리스트_추가() {
        WishRequestDto requestDTO = new WishRequestDto(1L);
        ProductResponseDto productResponseDto = new ProductResponseDto(1L, "상품 이름", 10000, "이미지 URL");
        when(productService.getAllProducts()).thenReturn(List.of(productResponseDto));
        Wish wish = new Wish(1L, 1L, 1L, productService);
        when(wishDao.insertWish(any(Wish.class))).thenReturn(wish);

        WishResponseDto addedWish = wishService.addWish(1L, requestDTO);

        assertNotNull(addedWish);
        assertEquals(1L, addedWish.productId);
        assertEquals("상품 이름", addedWish.productName);
    }

    @Test
    public void 위시리스트_추가_없는_상품() {
        WishRequestDto requestDTO = new WishRequestDto(100L);
        when(productService.getAllProducts()).thenReturn(List.of());

        assertThrows(BusinessException.class, () -> wishService.addWish(1L, requestDTO));
    }

    @Test
    public void 위시리스트_조회() {
        ProductResponseDto productResponseDto = new ProductResponseDto(1L, "상품 이름", 10000, "이미지 URL");
        when(productService.getAllProducts()).thenReturn(List.of(productResponseDto));
        Wish wish = new Wish(1L, 1L, 1L, productService);
        when(wishDao.selectWishesByUserId(1L)).thenReturn(List.of(wish));

        List<WishResponseDto> wishList = wishService.getWishesByUserId(1L);

        assertNotNull(wishList);
        assertEquals(1, wishList.size());
        assertEquals("상품 이름", wishList.get(0).productName);
    }

    @Test
    public void 위시리스트_삭제() {
        ProductResponseDto productResponseDto = new ProductResponseDto(1L, "상품 이름", 10000, "이미지 URL");
        when(productService.getAllProducts()).thenReturn(List.of(productResponseDto));
        Wish wish = new Wish(1L, 1L, 1L, productService);
        when(wishDao.selectWish(1L)).thenReturn(Optional.of(wish));
        doNothing().when(wishDao).deleteWish(1L);

        wishService.deleteWish(1L);

        verify(wishDao, times(1)).deleteWish(1L);
    }

    @Test
    public void 위시리스트_삭제_없는_위시() {
        when(wishDao.selectWish(100L)).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> wishService.deleteWish(100L));
    }
}
