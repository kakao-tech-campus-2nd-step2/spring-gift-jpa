package gift.service;

import gift.model.WishList;
import gift.model.WishListRepository;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * WhishListService 클래스는 WishList 관련 비즈니스 로직을 처리하는 서비스 클래스입니다
 */
@Service
public class WhishListService {

    private final WishListRepository wishListRepository;
    private final ProductService productService;

    /**
     * WhishListService 생성자
     *
     * @param wishListRepository WishListDAO 객체
     * @param productService
     */
    public WhishListService(WishListRepository wishListRepository, ProductService productService) {
        this.wishListRepository = wishListRepository;
        this.productService = productService;
    }

    /**
     * 새로운 WishList를 생성함
     *
     * @param productId WishList에 추가할 상품의 ID
     * @param userId    WishList에 추가할 사용자의 ID
     * @return 생성된 WishList 객체의 ID 리스트
     */
    public List<Long> createWishList(long productId, long userId) {
        productService.productNotFoundDetector(productId);
        WishList newWishList = wishListRepository.createWishList(productId, userId);
        return Collections.singletonList(newWishList.getProductId());
    }

    /**
     * 지정된 사용자의 모든 WishList를 조회함
     *
     * @param userId 조회할 사용자의 ID
     * @return 지정된 사용자의 모든 WishList 객체의 productId 리스트
     */
    public List<Long> getWishListsByUserId(long userId) {
        List<WishList> wishLists = wishListRepository.getWishListsByUserId(userId);
        return wishLists.stream()
                        .map(WishList::getProductId)
                        .collect(Collectors.toList());
    }

    /**
     * 지정된 사용자의 모든 WishList를 삭제함
     *
     * @param userId 삭제할 사용자의 ID
     * @return 삭제 성공 여부
     */
    public boolean deleteWishListsByUserId(long userId) {
        if (wishListRepository.deleteWishListsByUserId(userId)){
            return true;
        } else {
            return false;
        }
    }

    /**
     * 지정된 사용자가 지정된 상품을 위시리스트에서 삭제함
     *
     * @param userId    삭제할 사용자의 ID
     * @param productId 삭제할 상품의 ID
     * @return 삭제 성공 여부
     */
    public boolean deleteWishListByUserIdAndProductId(long userId, long productId) {
        productService.productNotFoundDetector(productId);
        return wishListRepository.deleteWishListByUserIdAndProductId(userId, productId);
    }

    /**
     * 지정된 사용자가 지정된 상품을 위시리스트에 추가함
     *
     * @param userId    추가할 사용자의 ID
     * @param productId 추가할 상품의 ID
     * @return 추가 성공 여부
     */
    public boolean addWishListByUserIdAndProductId(long userId, long productId) {
        productService.productNotFoundDetector(productId);
        return wishListRepository.addWishListByUserIdAndProductId(userId, productId);
    }
}
