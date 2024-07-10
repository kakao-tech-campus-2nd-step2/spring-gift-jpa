package gift.Service;

import gift.Model.RequestWishListDTO;
import gift.Model.ResponseWishListDTO;
import gift.Model.Member;
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
        wishListRepository.insertWishList(member.getEmail(), requestWishListDTO);
    }

    public List<ResponseWishListDTO> getWishList(Member member) {
        return wishListRepository.selectWishList(member.getEmail());
    }

    public List<ResponseWishListDTO> editWishList(Member member, RequestWishListDTO requestWishListDTO) {
        return wishListRepository.updateWishList(member.getEmail(), requestWishListDTO);
    }

    public List<ResponseWishListDTO> deleteWishList(Member member, RequestWishListDTO requestWishListDTO) {
        return wishListRepository.deleteWishList(member.getEmail(), requestWishListDTO);
    }
}
