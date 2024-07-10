package gift.wishlist.service;

import gift.product.service.ProductService;
import gift.wishlist.entity.WishList;
import gift.wishlist.dto.WishListReqDto;
import gift.wishlist.dto.WishListResDto;
import gift.wishlist.exception.WishListCreateException;
import gift.wishlist.exception.WishListDeleteException;
import gift.wishlist.exception.WishListNotFoundException;
import gift.wishlist.exception.WishListUpdateException;
import gift.wishlist.repository.WishListRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;
    private final ProductService productService;

    public WishListService(WishListRepository wishListRepository, ProductService productService) {
        this.wishListRepository = wishListRepository;
        this.productService = productService;
    }

    @Transactional(readOnly = true)
    public List<WishListResDto> getWishListsByMemberId(Long id) {

        return wishListRepository.findAllByMemberId(id).stream()
                .map(WishListResDto::new)
                .toList();
    }

    @Transactional
    public void addWishList(Long memberId, WishListReqDto wishListReqDto) {
        // 이미 위시 리스트에 있는 상품이면 수량을 더한다.
        if (wishListRepository.existsByMemberIdAndProductId(memberId, wishListReqDto.productId())) {
            addQuantity(memberId, wishListReqDto.productId(), wishListReqDto.quantity());
            return;
        }

        productService.findProductByIdOrThrow(wishListReqDto.productId());
        try {
            WishList wishList = wishListReqDto.toEntity(memberId);
            wishListRepository.save(wishList);
        } catch (Exception e) {
            throw WishListCreateException.EXCEPTION;
        }
    }

    private void addQuantity(Long memberId, Long productId, Integer quantity) {
        try {
            WishList findWishList = wishListRepository.findByMemberIdAndProductId(memberId, productId).orElseThrow(
                    () -> WishListNotFoundException.EXCEPTION
            );
            findWishList.addQuantity(quantity);
        } catch (Exception e) {
            throw WishListUpdateException.EXCEPTION;
        }
    }

    @Transactional
    public void updateWishListById(Long memberId, Long wishListId, WishListReqDto wishListReqDto) {
        // 수량이 0이면 삭제
        Integer quantity = wishListReqDto.quantity();
        if (quantity == 0) {
            deleteWishListById(memberId, wishListId);
            return;
        }

        WishList findWishList = findByIdAndMemberIdOrThrow(memberId, wishListId);
        try {
            findWishList.update(wishListReqDto);
        } catch (Exception e) {
            throw WishListUpdateException.EXCEPTION;
        }
    }

    @Transactional
    public void deleteWishListById(Long memberId, Long wishListId) {
        WishList findWishList = findByIdAndMemberIdOrThrow(memberId, wishListId);
        try {
            wishListRepository.delete(findWishList);
        } catch (Exception e) {
            throw WishListDeleteException.EXCEPTION;
        }
    }

    private WishList findByIdAndMemberIdOrThrow(Long memberId, Long wishListId) {
        return wishListRepository.findByIdAndMemberId(wishListId, memberId)
                .orElseThrow(() -> WishListNotFoundException.EXCEPTION);
    }
}
