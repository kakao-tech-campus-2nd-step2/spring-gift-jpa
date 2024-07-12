package gift.mapper;

import gift.DTO.WishListDTO;
import gift.model.wishlist.WishListEntity;
import gift.service.MemberService;
import gift.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WishListMapper {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    ProductService productService;

    @Autowired
    MemberService memberService;


    public WishListEntity toWishListEntity(WishListDTO wishListDTO) {
        var wishListEntity = new WishListEntity();
        wishListEntity.setId(wishListDTO.id());
        wishListEntity.setProductEntity(productMapper.toProductEntity(wishListDTO.productDTO()));
        wishListEntity.setMemberEntity(memberMapper.toMemberEntity(wishListDTO.memberDTO()));
        return wishListEntity;
    }

    public WishListDTO toWishListDTO(WishListEntity wishListEntity) {
        return new WishListDTO(
            wishListEntity.getId(),
            productMapper.toProductDTO(wishListEntity.getProductEntity()),
            memberMapper.toMemberDTO(wishListEntity.getMemberEntity())
        );
    }

    public WishListDTO toWishListDTOById(long productId, long userId) {
        var memberDTO = memberService.getMember(userId);
        var productDTO = productService.getProduct(productId);
        return new WishListDTO(0, productDTO, memberDTO);
    }

    public WishListEntity toWishListEntityById(long productId, long userId) {
        var WishListDTO = toWishListDTOById(productId, userId);
        return toWishListEntity(WishListDTO);
    }
}
