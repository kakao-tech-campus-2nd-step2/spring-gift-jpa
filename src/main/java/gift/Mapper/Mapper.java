package gift.Mapper;

import gift.Entity.Member;
import gift.Entity.Product;
import gift.Entity.Wishlist;
import gift.Entity.WishlistId;
import gift.Model.MemberDto;
import gift.Model.ProductDto;
import gift.Model.WishlistDto;
import gift.Service.ProductService;
import gift.Service.MemberService;
import gift.Service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Mapper {

    private final ProductService productService;
    private final MemberService memberService;
    private final WishlistService wishlistService;

    @Autowired
    public Mapper(@Lazy ProductService productService, @Lazy MemberService memberService, @Lazy WishlistService wishListService) {
        this.productService = productService;
        this.memberService = memberService;
        this.wishlistService = wishListService;
    }


    public Wishlist wishlistDtoToEntity(WishlistDto wishlistDto) {
        Optional<MemberDto> memberDtoOptional = memberService.findByUserId(wishlistDto.getUserId());
        MemberDto memberDto = memberDtoOptional.get();

        Optional<ProductDto> productDtoOptional = productService.getProductById(wishlistDto.getProductId());
        ProductDto productDto = productDtoOptional.get();

        WishlistId id = new WishlistId(memberDto.getId(), productDto.getId());
        return new Wishlist(id, memberDtoToEntity(memberDto), productDtoToEntity(productDto), wishlistDto.getProductName(), wishlistDto.getCount(), wishlistDto.getPrice());

    }

    public WishlistDto wishlistToDto(Wishlist wishlist) {
        return new WishlistDto(wishlist.getUserId(), wishlist.getProductId(), wishlist.getCount(), 0, wishlist.getProductName(), wishlist.getPrice());
    }

    public Product productDtoToEntity(ProductDto productDto) {
        return new Product(productDto.getId(), productDto.getName(), productDto.getPrice(), productDto.getImageUrl(), productDto.isDeleted());
    }

    public ProductDto productToDto(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), product.isDeleted());
    }

    public Member memberDtoToEntity(MemberDto memberDto) {
        return new Member(memberDto.getId(), memberDto.getEmail(), memberDto.getName(), memberDto.getPassword(), memberDto.isAdmin());
    }

    public MemberDto memberToDto(Member member) {
        return new MemberDto(member.getId(), member.getEmail(), member.getName(), member.getPassword(), member.isAdmin());
    }
}
