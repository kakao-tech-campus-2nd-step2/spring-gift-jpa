package gift;

import gift.Entity.Member;
import gift.Entity.Product;
import gift.Entity.Wishlist;
import gift.Mapper.Mapper;
import gift.Model.MemberDto;
import gift.Model.ProductDto;
import gift.Model.WishlistDto;
import gift.Repository.MemberJpaRepository;
import gift.Repository.ProductJpaRepository;
import gift.Repository.WishlistJpaRepository;
import gift.Service.MemberService;
import gift.Service.ProductService;
import gift.Service.WishlistService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class WishlistRepositoryTest {

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private WishlistJpaRepository wishlistJpaRepository;

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    private Mapper mapper;

    @BeforeEach
    void setUp() {
        ProductService productService = Mockito.mock(ProductService.class);
        MemberService memberService = Mockito.mock(MemberService.class);
        WishlistService wishlistService = Mockito.mock(WishlistService.class);

        // 예시 데이터 설정
        MemberDto mockMemberDto = new MemberDto(1L, "test@test.com", "testName", "testPassword", false);
        ProductDto mockProductDto = new ProductDto(1L, "testProduct", 1000, "testUrl", false);

        // 목 객체에 대한 반환 값 설정
        Mockito.when(memberService.findByUserId(Mockito.anyLong())).thenReturn(Optional.of(mockMemberDto));
        Mockito.when(productService.getProductById(Mockito.anyLong())).thenReturn(Optional.of(mockProductDto));

        mapper = new Mapper(productService, memberService, wishlistService);
    }

    @Test
    public void testGetWishlist() {
        MemberDto member1 = new MemberDto(1L, "1234@naver.com", "1234", "1234", false);
        Member member = mapper.memberDtoToEntity(member1);
        memberJpaRepository.save(member);

        WishlistDto wishlistDto = new WishlistDto(1L, 1L, 1, 0, "test", 1000);
        Wishlist wishlist = mapper.wishlistDtoToEntity(wishlistDto);
        Wishlist savedwishlist = wishlistJpaRepository.save(wishlist);

        assertThat(savedwishlist.getUserId()).isEqualTo(wishlist.getUserId());
        assertThat(savedwishlist.getProductId()).isEqualTo(wishlist.getProductId());
        assertThat(savedwishlist.getCount()).isEqualTo(wishlist.getCount());
        assertThat(savedwishlist.getPrice()).isEqualTo(wishlist.getPrice());
        assertThat(savedwishlist.getProductName()).isEqualTo(wishlist.getProductName());
    }

    @Test
    public void testAddWishlist() {
        WishlistDto wishlistDto = new WishlistDto(1L, 1L, 1, 0, "test", 1000);
        Wishlist wishlist = mapper.wishlistDtoToEntity(wishlistDto);
        Wishlist savedwishlist = wishlistJpaRepository.save(wishlist);

        assertThat(savedwishlist.getUserId()).isEqualTo(wishlist.getUserId());
        assertThat(savedwishlist.getProductId()).isEqualTo(wishlist.getProductId());
        assertThat(savedwishlist.getCount()).isEqualTo(wishlist.getCount());
        assertThat(savedwishlist.getPrice()).isEqualTo(wishlist.getPrice());
        assertThat(savedwishlist.getProductName()).isEqualTo(wishlist.getProductName());
    }

    @Test
    @DisplayName("delete가 정상적으로 이루어지는지")
    public void testRemoveWishlist() {
        MemberDto member1 = new MemberDto(1L, "1234@naver.com", "1234", "1234", false);
        Member member = mapper.memberDtoToEntity(member1);
        memberJpaRepository.save(member);

        ProductDto productDto1 = new ProductDto(1L, "productDto1", 1000, "http://localhost:8080/image1.jpg", false);
        Product product = mapper.productDtoToEntity(productDto1);
        productJpaRepository.save(product);

        WishlistDto wishlistDto = new WishlistDto(1L, 1L, 5, 0, "productDto1", 1000);
        Wishlist wishlist = mapper.wishlistDtoToEntity(wishlistDto);
        Wishlist savedwishlist = wishlistJpaRepository.save(wishlist);

        wishlistJpaRepository.delete(savedwishlist);

        Optional<Wishlist> foundWishlist = wishlistJpaRepository.findByWishlistId(savedwishlist.getUserId(), savedwishlist.getProductId());

        assertThat(foundWishlist).isEmpty();

    }

    @Test
    @DisplayName("Update가 정상적으로 이루어지는지")
    public void testUpdateWishlistItem() {
        MemberDto member1 = new MemberDto(1L, "1234@naver.com", "1234", "1234", false);
        Member member = mapper.memberDtoToEntity(member1);
        memberJpaRepository.save(member);

        ProductDto productDto1 = new ProductDto(1L, "productDto1", 1000, "http://localhost:8080/image1.jpg", false);
        Product product = mapper.productDtoToEntity(productDto1);
        productJpaRepository.save(product);

        WishlistDto wishlistDto = new WishlistDto(1L, 1L, 5, 0, "productDto1", 5000);
        Wishlist wishlist = mapper.wishlistDtoToEntity(wishlistDto);
        wishlistJpaRepository.save(wishlist);

        //수량을 5개에서 3개로 변경
        WishlistDto updateDto = new WishlistDto(1L, 1L, 3, 0, "test", 3000);
        Wishlist updateWishlist = mapper.wishlistDtoToEntity(updateDto);

        wishlistJpaRepository.save(updateWishlist);

        Optional<Wishlist> foundWishlistOptional = wishlistJpaRepository.findByWishlistId(updateWishlist.getUserId(), updateWishlist.getProductId());
        Wishlist foundWishlist = foundWishlistOptional.get();
        assertThat(foundWishlist.getUserId()).isEqualTo(updateWishlist.getUserId());
        assertThat(foundWishlist.getProductId()).isEqualTo(updateWishlist.getProductId());
        assertThat(foundWishlist.getCount()).isEqualTo(updateWishlist.getCount());
        assertThat(foundWishlist.getProductName()).isEqualTo(updateWishlist.getProductName());
        assertThat(foundWishlist.getPrice()).isEqualTo(updateWishlist.getPrice());

    }
}
