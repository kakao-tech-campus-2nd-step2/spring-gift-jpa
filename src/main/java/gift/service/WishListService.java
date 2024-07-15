package gift.service;

import gift.domain.ProductDTO;
import gift.entity.MemberEntity;
import gift.entity.ProductEntity;
import gift.entity.WishListEntity;
import gift.domain.WishListDTO;
import gift.repository.WishListRepository;
import java.util.Optional;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishListService {
    private final WishListRepository wishListRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public WishListService(WishListRepository wishListRepository,
        WishListRepository wishListRepository1, ProductRepository productRepository, MemberRepository memberRepository) {
        this.wishListRepository = wishListRepository1;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    private WishListDTO toWishListDTO(WishListEntity wishListEntity) {
        return new WishListDTO(wishListEntity.getProductEntity().getId(), wishListEntity.getUserEntity().getId());
    }

    private WishListEntity dtoToEntity(Long userId, ProductDTO product) throws Exception {
        MemberEntity memberEntity = memberRepository.findById(userId)
            .orElseThrow(() -> new Exception("유저가 존재하지 않습니다."));

        ProductEntity productEntity = productRepository.findById(product.getId())
            .orElseThrow(() -> new Exception("상품이 존재하지 않습니다."));

        return new WishListEntity(productEntity, memberEntity);
    }

    public Page<WishListDTO> readWishList(Long userId, Pageable pageable) {
        Page<WishListEntity> wishListEntities = wishListRepository.findByUserEntity_Id(userId, pageable);
        return wishListEntities.map(this::toWishListDTO);
    }

    @Transactional
    public void addProductToWishList(Long userId, ProductDTO product) throws Exception {
        wishListRepository.save(dtoToEntity(userId, product));
    }

    @Transactional
    public void removeWishList(Long userId) {
        List<WishListEntity> wishListEntities = wishListRepository.findByUserEntity_Id(userId,Pageable.unpaged()).getContent();
        wishListRepository.deleteAll(wishListEntities);
    }

    @Transactional
    public void removeProductFromWishList(Long userId, Long productId) {
        Optional<WishListEntity> wishListEntityOpt = wishListRepository.findByUserEntity_IdAndProductEntity_Id(userId, productId);
        wishListEntityOpt.ifPresent(wishListRepository::delete);
    }

}
