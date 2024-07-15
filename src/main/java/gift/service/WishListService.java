package gift.service;

import gift.DTO.ProductDTO;
import gift.DTO.WishListDTO;
import gift.aspect.CheckProductExists;
import gift.auth.DTO.MemberDTO;
import gift.mapper.WishListMapper;
import gift.model.wishlist.WishListEntity;
import gift.model.wishlist.WishListRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * WhishListService 클래스는 WishList 관련 비즈니스 로직을 처리하는 서비스 클래스입니다
 */
@Service
@Transactional
public class WishListService {

    private final WishListRepository wishListRepository;

    private final WishListMapper wishListMapper;

    /**
     * WhishListService 생성자
     *
     * @param wishListRepository WishListDAO 객체
     */
    public WishListService(WishListRepository wishListRepository, WishListMapper wishListMapper) {
        this.wishListRepository = wishListRepository;
        this.wishListMapper = wishListMapper;
    }

    /**
     * 새로운 WishList를 추가함
     *
     * @param productId WishList에 추가할 상품의 ID
     * @param memberDTO WishList를 추가할 사용자의 정보
     * @return 생성된 WishList 객체의 ID 리스트
     */
    @CheckProductExists
    public ProductDTO addWishList(long productId, MemberDTO memberDTO) {
        var wishListEntity = wishListMapper.toWishListEntity(productId, memberDTO);
        wishListRepository.save(wishListEntity);
        return wishListMapper.toWishListDTO(wishListEntity).productDTO();
    }

    /**
     * 지정된 사용자의 모든 WishList를 조회함
     *
     * @param userId 조회할 사용자의 ID
     * @return 지정된 사용자의 모든 WishList 객체의 productId 리스트
     */
    public List<ProductDTO> getWishListsByUserId(Long userId) {
        List<WishListEntity> wishListEntities = wishListRepository.findAllByMemberEntityId(userId);

        return wishListEntities.stream()
            .map(wishListMapper::toWishListDTO)
            .map(WishListDTO::productDTO)
            .collect(Collectors.toList());
    }

    /**
     * 주어진 사용자 ID와 페이징 정보를 기반으로 사용자의 위시리스트를 가져옵니다.
     *
     * @param userId   사용자 ID
     * @param pageable 페이징 정보
     * @return 사용자 위시리스트의 ProductDTO 목록
     */
    public List<ProductDTO> getWishListsByUserId(Long userId, Pageable pageable) {
        List<WishListEntity> wishListEntities = wishListRepository.findAllByMemberEntityId(userId,
            pageable);
        return wishListEntities.stream()
            .map(wishListMapper::toWishListDTO)
            .map(WishListDTO::productDTO)
            .collect(Collectors.toList());
    }

    /**
     * 지정된 사용자의 모든 WishList를 삭제함
     *
     * @param userId 삭제할 사용자의 ID
     * @return 삭제 성공 여부
     */
    public boolean deleteWishListsByUserId(long userId) {
        return wishListRepository.deleteWishListsByMemberEntityId(userId) > 0;
    }

    /**
     * 지정된 사용자가 지정된 상품을 위시리스트에서 삭제함
     *
     * @param userId    삭제할 사용자의 ID
     * @param productId 삭제할 상품의 ID
     * @return 삭제 성공 여부
     */
    @CheckProductExists
    public boolean deleteWishListByUserIdAndProductId(long productId, long userId) {
        return
            wishListRepository.deleteWishListByMemberEntityIdAndProductEntityId(userId, productId)
                > 0;
    }
}
