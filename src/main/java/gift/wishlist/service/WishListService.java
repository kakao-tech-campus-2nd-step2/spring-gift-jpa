package gift.wishlist.service;

import gift.wishlist.dto.WishListReqDto;
import gift.wishlist.dto.WishListResDto;
import gift.wishlist.exception.WishListCreateException;
import gift.wishlist.exception.WishListDeleteException;
import gift.wishlist.exception.WishListNotFoundException;
import gift.wishlist.exception.WishListUpdateException;
import gift.wishlist.repository.WishListRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;

    public WishListService(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    public List<WishListResDto> getWishListsByMemberId(Long id) {

        return wishListRepository.findWishListsByMemberId(id).stream()
                .map(WishListResDto::new)
                .toList();
    }

    public void addWishList(Long memberId, WishListReqDto wishListReqDto) {
        // 이미 위시 리스트에 있는 상품이면 수량을 더한다.
        if (wishListRepository.isWishListExistByMemberIdAndProductId(memberId, wishListReqDto.productId())) {
            addQuantity(memberId, wishListReqDto.productId(), wishListReqDto.quantity());
            return;
        }

        try {
            wishListRepository.addWishList(memberId, wishListReqDto.productId(), wishListReqDto.quantity());
        } catch (Exception e) {
            throw WishListCreateException.EXCEPTION;
        }
    }

    private void addQuantity(Long memberId, Long productId, Integer quantity) {
        try {
            wishListRepository.addQuantityByMemberIdAndProductId(memberId, productId, quantity);
        } catch (Exception e) {
            throw WishListUpdateException.EXCEPTION;
        }
    }

    public void updateWishListById(Long memberId, Long wishListId, WishListReqDto wishListReqDto) {
        // 수량이 0이면 삭제
        Integer quantity = wishListReqDto.quantity();
        if (quantity == 0) {
            deleteWishListById(memberId, wishListId);
            return;
        }

        validateWishListByMemberIdAndWishListId(memberId, wishListId);
        try {
            wishListRepository.updateWishListById(wishListId, quantity);
        } catch (Exception e) {
            throw WishListUpdateException.EXCEPTION;
        }
    }

    public void deleteWishListById(Long memberId, Long wishListId) {
        validateWishListByMemberIdAndWishListId(memberId, wishListId);
        try {
            wishListRepository.deleteWishListById(wishListId);
        } catch (Exception e) {
            throw WishListDeleteException.EXCEPTION;
        }
    }

    private void validateWishListByMemberIdAndWishListId(Long memberId, Long wishListId) {
        if (!wishListRepository.isWishListExistByMemberIdAndWishListId(memberId, wishListId)) {
            throw WishListNotFoundException.EXCEPTION;
        }
    }
}
