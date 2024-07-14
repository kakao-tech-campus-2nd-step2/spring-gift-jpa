package gift;

import gift.Entity.Product;
import gift.Entity.Member;
import gift.Entity.Wishlist;
import gift.Mapper.Mapper;
import gift.Model.MemberDto;
import gift.Model.ProductDto;
import gift.Model.WishlistDto;
import gift.Repository.ProductJpaRepository;
import gift.Repository.MemberJpaRepository;
import gift.Repository.WishlistJpaRepository;
import gift.Service.MemberService;
import gift.Service.ProductService;
import gift.Service.WishlistService;
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
    private WishlistJpaRepository wishListJpaRepository;
    @Autowired
    private MemberJpaRepository memberJpaRepository;
    @Autowired
    private ProductJpaRepository productJpaRepository;

    private Mapper mapper;

    @BeforeEach
    void setUp() {
        // 필요한 서비스의 목 객체 생성
        ProductService productService = Mockito.mock(ProductService.class);
        MemberService memberService = Mockito.mock(MemberService.class);
        WishlistService wishListService = Mockito.mock(WishlistService.class);

        MemberDto mockMemberDto = new MemberDto(1L, "test@test.com", "testName", "testPassword", false);
        ProductDto mockProductDto = new ProductDto(1L, "testProduct", 1000, "testUrl", false);

        // 목 객체에 대한 반환 값 설정
        Mockito.when(memberService.findByUserId(Mockito.anyLong())).thenReturn(Optional.of(mockMemberDto));
        Mockito.when(productService.getProductById(Mockito.anyLong())).thenReturn(Optional.of(mockProductDto));

        // Mapper 인스턴스 수동 생성 및 주입
        mapper = new Mapper(productService, memberService, wishListService);
    }

    @Test
    public void testGetWishlist() {
        MemberDto memberDto1 = new MemberDto(1L, "1234@naver.com", "1234", "1234", false);
        Member member1 = mapper.memberDtoToEntity(memberDto1);
        memberJpaRepository.save(member1);

        WishlistDto wishlistDto = new WishlistDto(1L, 1L, 1, 0, "test", 1000);
        Wishlist wishlist = mapper.wishlistDtoToEntity(wishlistDto);
        Wishlist savedwishlist = wishListJpaRepository.save(wishlist);

        assertThat(savedwishlist.getUserId()).isEqualTo(wishlist.getUserId());
        assertThat(savedwishlist.getProductId()).isEqualTo(wishlist.getProductId());
        assertThat(savedwishlist.getCount()).isEqualTo(wishlist.getCount());
        assertThat(savedwishlist.getPrice()).isEqualTo(wishlist.getPrice());
        assertThat(savedwishlist.getProductName()).isEqualTo(wishlist.getProductName());
    }

    @Test
    public void testAddWishlistItem() {
        WishlistDto wishlistDto = new WishlistDto(1L, 1L, 1, 0, "test", 1000);
        Wishlist wishlist = mapper.wishlistDtoToEntity(wishlistDto);
        Wishlist savedwishlist = wishListJpaRepository.save(wishlist);

        assertThat(savedwishlist.getUserId()).isEqualTo(wishlist.getUserId());
        assertThat(savedwishlist.getProductId()).isEqualTo(wishlist.getProductId());
        assertThat(savedwishlist.getCount()).isEqualTo(wishlist.getCount());
        assertThat(savedwishlist.getPrice()).isEqualTo(wishlist.getPrice());
        assertThat(savedwishlist.getProductName()).isEqualTo(wishlist.getProductName());
    }

    @Test
    @DisplayName("delete가 정상적으로 이루어지는지")
    public void testRemoveWishlistItem() {
        ProductDto productDto1 = new ProductDto(1L, "productDto1", 1000, "http://localhost:8080/image1.jpg", false);
        Product product = mapper.productDtoToEntity(productDto1);
        productJpaRepository.save(product);

        MemberDto memberDto1 = new MemberDto(1L, "1234@naver.com", "1234", "1234", false);
        memberJpaRepository.save(mapper.memberDtoToEntity(memberDto1));

        WishlistDto wishlistDto = new WishlistDto(1L, 1L, 5, 0, "test", 1000);
        Wishlist wishlist = mapper.wishlistDtoToEntity(wishlistDto);
        Wishlist savedwishlist = wishListJpaRepository.save(wishlist);

        wishListJpaRepository.delete(savedwishlist);

        Optional<Wishlist> foundWishlist = wishListJpaRepository.findByWishlistId(savedwishlist.getUserId(), savedwishlist.getProductId());

        assertThat(foundWishlist).isEmpty();

    }

    @Test
    @DisplayName("Update가 정상적으로 이루어지는지")
    public void testUpdateWishlistItem() {
        ProductDto productDto1 = new ProductDto(1L, "productDto1", 1000, "http://localhost:8080/image1.jpg", false);
        Product product = mapper.productDtoToEntity(productDto1);
        productJpaRepository.save(product);

        MemberDto memberDto1 = new MemberDto(1L, "1234@naver.com", "1234", "1234", false);
        memberJpaRepository.save(mapper.memberDtoToEntity(memberDto1));

        WishlistDto wishlistDto = new WishlistDto(1L, 1L, 5, 0, "test", 5000);
        Wishlist wishlist = mapper.wishlistDtoToEntity(wishlistDto);
        wishListJpaRepository.save(wishlist);

        //수량을 5개에서 3개로 변경
        WishlistDto updateItem = new WishlistDto(1L, 1L, 3, 0, "test", 3000);
        Wishlist updateWishlist = mapper.wishlistDtoToEntity(updateItem);

        wishListJpaRepository.save(updateWishlist);

        Optional<Wishlist> foundWishlistOptional = wishListJpaRepository.findByWishlistId(updateWishlist.getUserId(), updateWishlist.getProductId());
        Wishlist foundWishlist = foundWishlistOptional.get();
        assertThat(foundWishlist.getUserId()).isEqualTo(updateWishlist.getUserId());
        assertThat(foundWishlist.getProductId()).isEqualTo(updateWishlist.getProductId());
        assertThat(foundWishlist.getCount()).isEqualTo(updateWishlist.getCount());
        assertThat(foundWishlist.getProductName()).isEqualTo(updateWishlist.getProductName());
        assertThat(foundWishlist.getPrice()).isEqualTo(updateWishlist.getPrice());

    }
}
