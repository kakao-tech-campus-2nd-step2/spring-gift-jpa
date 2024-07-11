package gift.service;

import gift.DTO.WishProductDTO;
import gift.domain.WishProduct;
import gift.repository.WishListRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishListService {
    private final WishListRepository wishListRepository;

    public WishListService(WishListRepository wishListRepository){
        this.wishListRepository = wishListRepository;
    }
    /*
     * 특정 상품을 위시리스트에 추가하는 로직
     */
    public void addWishList(WishProductDTO wishProductDTO){
        String userId = wishProductDTO.getUserId();
        Long productId = wishProductDTO.getProductId();

        if(wishListRepository.existsByUserIdAndProductId(userId, productId)){
            WishProduct wishProduct = wishListRepository.findByUserIdAndProductId(userId, productId);
            wishProduct.changeCount(wishProduct.getCount() + 1);
            wishListRepository.save(wishProduct);
            return;
        }

        WishProduct wishProduct = new WishProduct(userId, productId);
        wishListRepository.save(wishProduct);
    }
    /*
     * 특정 유저의 위시리스트를 반환하는 로직
     */
    public List<WishProductDTO> loadWishList(String userId){
        List<WishProductDTO> list = new ArrayList<>();

        List<WishProduct> wishes = wishListRepository.findByUserId(userId);
        for (WishProduct wishProduct : wishes) {
            WishProductDTO wishProductDTO = new WishProductDTO(
                    wishProduct.getUserId(),
                    wishProduct.getProductId(),
                    wishProduct.getCount()
            );
            list.add(wishProductDTO);
        }

        return list;
    }
    /*
     * 특정 유저의 특정 위시리스트 물품의 수량을 변경하는 로직
     */
    public void updateWishProduct(String userId, Long productId, int count){
        WishProduct wish = wishListRepository.findByUserIdAndProductId(userId, productId);
        wish.changeCount(count);
        wishListRepository.save(wish);
    }
    /*
     * 특정 유저의 특정 위시리스트 물품을 삭제하는 로직
     */
    @Transactional
    public void deleteWishProduct(String userId, Long productId){
        wishListRepository.deleteByUserIdAndProductId(userId, productId);
    }

}
