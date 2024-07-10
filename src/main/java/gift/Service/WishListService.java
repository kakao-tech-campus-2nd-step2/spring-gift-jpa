package gift.Service;

import gift.Model.*;
import gift.Repository.WishListRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WishListService {
    private final WishListRepository wishListRepository;


    public WishListService(WishListRepository wishListRepository){
        this.wishListRepository = wishListRepository;
    }

    public void addWishList(Member member, RequestWishListDTO requestWishListDTO) {
        WishList wishList = new WishList(member.getEmail(), requestWishListDTO.getProductId(), requestWishListDTO.getCount());
        wishListRepository.save(wishList);
    }

    public List<ResponseWishListDTO> getWishList(Member member) {
        return wishListRepository.findWishListsByEmail(member.getEmail());
    }

    public List<ResponseWishListDTO> editWishList(Member member, RequestWishListDTO requestWishListDTO) {
        wishListRepository.updateWishListCount(requestWishListDTO.getCount(), member.getEmail(), requestWishListDTO.getProductId());
        return getWishList(member);
    }

    public List<ResponseWishListDTO> deleteWishList(Member member, RequestWishListDTO requestWishListDTO) {
        wishListRepository.deleteWishListByEmailAndProductId(member.getEmail(), requestWishListDTO.getProductId());
        return getWishList(member);
    }
}
