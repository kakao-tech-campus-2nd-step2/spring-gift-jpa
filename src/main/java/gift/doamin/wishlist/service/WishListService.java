package gift.doamin.wishlist.service;

import gift.doamin.wishlist.dto.WishListForm;
import gift.doamin.wishlist.entity.WishList;
import gift.doamin.wishlist.exception.InvalidWishListFormException;
import gift.doamin.wishlist.exception.WishListNotFoundException;
import gift.doamin.wishlist.repository.JpaWishListRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class WishListService {

    private final JpaWishListRepository wishListRepository;

    public WishListService(JpaWishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    public WishList create(Long userId, WishListForm wishListForm) {
        Long productId = wishListForm.getProductId();
        if (wishListRepository.existsByUserIdAndProductId(userId, productId)) {
            throw new InvalidWishListFormException("동일한 상품을 위시리스트에 또 넣을수는 없습니다");
        }
        // 수량 0개는 등록 불가
        if (wishListForm.isZeroQuantity()) {
            throw new InvalidWishListFormException("위시리스트에 상품 0개를 넣을수는 없습니다");
        }
        var wishList = new WishList(userId, wishListForm.getProductId(),
            wishListForm.getQuantity());
        return wishListRepository.save(wishList);
    }

    public List<WishList> read(Long userId) {
        return wishListRepository.findAllByUserId(userId);
    }

    public void update(Long userId, WishListForm wishListForm) {
        Long productId = wishListForm.getProductId();

        // 수량을 지정하지 않았거나 0으로 수정하는 경우 위시리스트에서 삭제
        if (wishListForm.isZeroQuantity()) {
            delete(userId, productId);
            return;
        }

        // 해당 위시리스트가 존재하지 않으면(사용자의 위시리스트에 해당 상품이 없으면) 새로 생성
        Optional<WishList> wishList = wishListRepository.findByUserIdAndProductId(userId,
            productId);
        if (wishList.isEmpty()) {
            create(userId, wishListForm);
            return;
        }

        WishList updatedWishList = new WishList(userId, productId, wishListForm.getQuantity());
        updatedWishList.setId(wishList.get().getId());
        wishListRepository.save(updatedWishList);
    }

    public void delete(Long userId, Long productId) {
        WishList wishList = wishListRepository.findByUserIdAndProductId(userId, productId)
            .orElseThrow(() -> new WishListNotFoundException("위시리스트에 삭제할 상품이 존재하지 않습니다."));

        wishListRepository.deleteById(wishList.getId());
    }
}
