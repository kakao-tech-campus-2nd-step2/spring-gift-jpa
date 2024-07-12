package gift.service;

import gift.entity.WishListEntity;
import gift.domain.WishListDTO;
import gift.repository.WishListRepository;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListService {

    @Autowired
    private WishListRepository wishListRepository;

    private WishListDTO toWishListDTO(WishListEntity wishListEntity) {
        return new WishListDTO(wishListEntity.getProductId(), wishListEntity.getUserId());
    }

    public List<WishListDTO> readWishList(Long userId) {
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
        Optional<WishListEntity> wishListEntityOpt = wishListRepository.findByUserIdAndProductId(userId, productId);
        wishListEntityOpt.ifPresent(wishListRepository::delete);
    }

}
