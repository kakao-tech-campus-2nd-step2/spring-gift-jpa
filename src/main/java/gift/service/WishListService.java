package gift.service;

import gift.entity.MemberEntity;
import gift.entity.ProductEntity;
import gift.entity.WishListEntity;
import gift.model.Product;
import gift.model.WishList;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishListRepository;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public WishListService(WishListRepository wishListRepository, ProductRepository productRepository, MemberRepository memberRepository) {
        this.wishListRepository = wishListRepository;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    private WishList toWishListDTO(WishListEntity wishListEntity) {
        return new WishList(wishListEntity.getProductEntity().getId(), wishListEntity.getUserEntity().getId());
    }

    private WishListEntity dtoToEntity(Long userId, Product product) throws Exception {
        MemberEntity memberEntity = memberRepository.findById(userId).orElseThrow(null);
        if (memberEntity == null) {
            throw new Exception("유저가 존재하지 않습니다.");
        }
        ProductEntity productEntity = productRepository.findById(product.getId()).orElseThrow(null);
        if (productEntity == null) {
            throw new Exception("상품이 존재하지 않습니다.");
        }
        return new WishListEntity(productEntity, memberEntity);
    }

    public List<WishList> readWishList(Long userId) {
        List<WishListEntity> wishListEntities = wishListRepository.findByUserId(userId);
        return wishListEntities.stream()
            .map(this::toWishListDTO)
            .collect(Collectors.toList());
    }

    public void addProductToWishList(Long userId, Product product) throws Exception {
        wishListRepository.save(dtoToEntity(userId, product));
    }

    public void removeWishList(Long userId) {
        List<WishListEntity> wishListEntities = wishListRepository.findByUserId(userId);
        wishListRepository.deleteAll(wishListEntities);
    }

    public void removeProductFromWishList(Long userId, Long productId) {
        WishListEntity wishListEntity = wishListRepository.findByUserIdAndProductId(userId, productId);
        if (wishListEntity != null) {
            wishListRepository.delete(wishListEntity);
        }
    }
}
