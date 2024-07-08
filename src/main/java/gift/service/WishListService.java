package gift.service;

import gift.DTO.WishProductDTO;
import gift.domain.WishProduct;
import gift.repository.WishListRepository;
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
        WishProduct wishProduct = new WishProduct(
                wishProductDTO.getUserId(),
                wishProductDTO.getProductId()
        );

        wishListRepository.save(wishProduct);
    }
    /*
     * 특정 유저의 위시리스트를 반환하는 로직
     */
    public List<WishProductDTO> loadWishList(String userId){
        List<WishProductDTO> list = new ArrayList<>();

        List<WishProduct> byEmail = wishListRepository.findByUserId(userId);
        for (WishProduct wishProduct : byEmail) {
            WishProductDTO wishProductDTO = new WishProductDTO(
                    wishProduct.getUserId(),
                    wishProduct.getProductId()
            );
            list.add(wishProductDTO);
        }

        return list;
    }
    /*
     * 특정 유저의 특정 위시리스트 물품을 삭제하는 로직
     */
    public void deleteWishProduct(String email, Long id){
        wishListRepository.delete(email, id);
    }

}
