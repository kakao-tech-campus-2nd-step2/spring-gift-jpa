package gift.service;

import gift.entity.WishListEntity;
import gift.model.WishList;
import gift.repository.WishListRepository;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListService {

    @Autowired
    private WishListRepository wishListRepository;

    private WishList toWishListDTO(WishListEntity wishListEntity) {
        return new WishList(wishListEntity.getProductId(), wishListEntity.getUserId());
    }

    public List<WishList> readWishList(Long userId) {
        List<WishListEntity> wishListEntities = wishListRepository.findByUserId(userId);
        return wishListEntities.stream()
            .map(this::toWishListDTO)
            .collect(Collectors.toList());

    }

    public void addProductToWishList(Long userId, Long productId) {
        WishListEntity wishListEntity = new WishListEntity(productId, userId);
        wishListRepository.save(wishListEntity);
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
