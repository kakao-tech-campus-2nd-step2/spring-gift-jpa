package gift.service;

import gift.DTO.WishListDTO;
import gift.model.wishlist.WishListEntity;
import gift.aspect.CheckProductExists;
import gift.model.wishlist.WishListRepository;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * WhishListService 클래스는 WishList 관련 비즈니스 로직을 처리하는 서비스 클래스입니다
 */
@Service
public class WishListService {

    private final WishListRepository wishListRepository;

    /**
     * WhishListService 생성자
     *
     * @param wishListRepository WishListDAO 객체
     */
    public WishListService(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    private WishListDTO toWishListDTO(WishListEntity wishListEntity) {
        return new WishListDTO(
            wishListEntity.getUserId(),
            wishListEntity.getProductId()
        );
    }

    private WishListEntity toWishListEntity(WishListDTO wishListDTO) {
        var wishListEntity = new WishListEntity();
        wishListEntity.setUserId(wishListDTO.getUserId());
        wishListEntity.setProductId(wishListDTO.getProductId());
        return wishListEntity;
    }

    /**
     * 새로운 WishList를 생성함
     *
     * @param productId WishList에 추가할 상품의 ID
     * @param userId    WishList에 추가할 사용자의 ID
     * @return 생성된 WishList 객체의 ID 리스트
     */
    @CheckProductExists
    public List<Long> createWishList(long productId, long userId) {
        var wishListEntity = new WishListEntity();
        wishListEntity.setProductId(productId);
        wishListEntity.setUserId(userId);
        wishListRepository.save(wishListEntity);
        return Collections.singletonList(wishListEntity.getId());
    }

    /**
     * 지정된 사용자의 모든 WishList를 조회함
     *
     * @param userId 조회할 사용자의 ID
     * @return 지정된 사용자의 모든 WishList 객체의 productId 리스트
     */
    public List<Long> getWishListsByUserId(long userId) {
        var wishListEntities = wishListRepository.findAllByUserId(userId);
        return wishListEntities.stream()
            .map(WishListEntity::getProductId)
            .collect(Collectors.toList());
    }

    /**
     * 지정된 사용자의 모든 WishList를 삭제함
     *
     * @param userId 삭제할 사용자의 ID
     * @return 삭제 성공 여부
     */
    public boolean deleteWishListsByUserId(long userId) {
        return wishListRepository.deleteWishListsByUserId(userId) > 0;
    }

    /**
     * 지정된 사용자가 지정된 상품을 위시리스트에서 삭제함
     *
     * @param userId    삭제할 사용자의 ID
     * @param productId 삭제할 상품의 ID
     * @return 삭제 성공 여부
     */
    @CheckProductExists
    public boolean deleteWishListByUserIdAndProductId(long userId, long productId) {
        return wishListRepository.deleteWishListByUserIdAndProductId(userId, productId) > 0;
    }

    /**
     * 지정된 사용자가 지정된 상품을 위시리스트에 추가함
     *
     * @param userId    추가할 사용자의 ID
     * @param productId 추가할 상품의 ID
     * @return 추가 성공 여부
     */
    @CheckProductExists
    public boolean addWishListByUserIdAndProductId(long userId, long productId) {
        var wishListEntity = new WishListEntity();
        wishListEntity.setUserId(userId);
        wishListEntity.setProductId(productId);
        wishListRepository.save(wishListEntity);
        return true;
    }
}
